package io.github.aifiltration.routes.games

import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.models.user
import io.github.aifiltration.models.userGame
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.komapper.core.dsl.QueryDsl

@Serializable
data class LeaderboardEntryPayload(
	val gamePlayed: Int,
	val points: Int,
	val rank: Int,
	val username: String,
)

fun Route.leaderboard() = get("/leaderboard") {
	val entityStore = database.runQuery {
		QueryDsl.from(Tables.user).innerJoin(Tables.userGame) {
			Tables.user.id eq Tables.userGame.userId
		}.includeAll()
	}

	val userGames = entityStore.oneToMany(Tables.user, Tables.userGame)
	val ranks = userGames.map { (user, userGame) ->
		user.id to userGame.sumOf { it.points }
	}.sortedByDescending { (_, points) -> points }.mapIndexed { index, (id, _) ->
		id to index + 1
	}.toMap()

	val leaderboard = userGames.map { (user, userGame) ->
		LeaderboardEntryPayload(
			gamePlayed = userGame.size,
			points = userGame.sumOf { it.points },
			rank = ranks[user.id] ?: (ranks.size + 1),
			username = user.username,
		)
	}

	call.respond(HttpStatusCode.OK, leaderboard)
}
