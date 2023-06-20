package io.github.aifiltration.routes.votes

import io.github.aifiltration.ai.AI_ID
import io.github.aifiltration.currentGame
import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.gameCooldown
import io.github.aifiltration.models.User
import io.github.aifiltration.models.userGame
import io.github.aifiltration.models.vote
import io.github.aifiltration.usedNames
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.isDistantPast
import kotlinx.serialization.Serializable
import org.komapper.core.dsl.QueryDsl

@Serializable
data class VotePayload(
	val authorId: Int,
	val targetUser: User?,
	val valid: Boolean,
)

fun Route.votes() = get("/games/{gameId}/votes") {
	val id = call.parameters["gameId"]?.toIntOrNull() ?: run {
		call.respond(HttpStatusCode.BadRequest)
		return@get
	}

	if (currentGame.id != id) {
		call.respond(HttpStatusCode.Forbidden, "You can only see votes for the current game.")
		return@get
	}

	if (gameCooldown.isDistantPast) {
		call.respond(HttpStatusCode.Forbidden, "Game is not finished yet.")
		return@get
	}

	val entityStore = database.runQuery {
		QueryDsl.from(Tables.userGame).where {
			Tables.userGame.gameId eq id
		}.leftJoin(Tables.vote) {
			Tables.userGame.userId eq Tables.vote.authorId
			Tables.vote.gameId eq id
		}.includeAll()
	}

	val members = entityStore[Tables.userGame]
	val votes = entityStore[Tables.vote]
	val mappedVotes = members.map { userGame ->
		val userId = userGame.userId
		val vote = votes.find { it.authorId == userId } ?: return@map VotePayload(
			authorId = userId,
			targetUser = null,
			valid = false,
		)

		VotePayload(
			authorId = userId,
			targetUser = User(vote.targetId, usedNames[vote.targetId]!!),
			valid = vote.targetId == AI_ID,
		)
	}

	call.respond(HttpStatusCode.OK, mappedVotes)
}
