package io.github.aifiltration.routes.votes

import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.gameCooldown
import io.github.aifiltration.models.Vote
import io.github.aifiltration.models.vote
import io.github.aifiltration.plugins.UserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.datetime.isDistantPast
import kotlinx.serialization.Serializable
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.firstOrNull

@Serializable
data class CreateVotePayload(val targetId: Int)

fun Route.vote() = post("/games/{gameId}/vote") {
	val gameId = call.parameters["gameId"]?.toIntOrNull() ?: run {
		call.respond(HttpStatusCode.BadRequest)
		return@post
	}

	val userId = call.sessions.get<UserSession>()?.userId ?: run {
		call.respond(HttpStatusCode.Unauthorized)
		return@post
	}

	if (!gameCooldown.isDistantPast) {
		call.respond(HttpStatusCode.Forbidden, "Game is in cooldown.")
		return@post
	}

	val vote = call.receive<CreateVotePayload>()

	val currentVote = database.runQuery {
		QueryDsl.from(Tables.vote).where {
			Tables.vote.gameId eq gameId
			Tables.vote.authorId eq userId
			Tables.vote.targetId eq vote.targetId
		}.firstOrNull()
	}

	if (currentVote != null) {
		call.respond(HttpStatusCode.BadRequest, "You have already voted for this player.")
		return@post
	}

	runCatching {
		database.runQuery {
			QueryDsl.insert(Tables.vote).single(
				Vote(
					gameId = gameId,
					authorId = userId,
					targetId = vote.targetId
				)
			)
		}
	}.onFailure {
		call.respond(HttpStatusCode.BadRequest, "Player does not exist.")
		return@post
	}

	call.respond(HttpStatusCode.OK)
}
