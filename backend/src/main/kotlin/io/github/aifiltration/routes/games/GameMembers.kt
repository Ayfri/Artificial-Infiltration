package io.github.aifiltration.routes.games

import io.github.aifiltration.MAX_PLAYERS
import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.models.UserGame
import io.github.aifiltration.models.game
import io.github.aifiltration.models.user
import io.github.aifiltration.models.userGame
import io.github.aifiltration.plugins.UserSession
import io.github.aifiltration.waitingList
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.komapper.core.UniqueConstraintException
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.first

fun Route.joinGame() = post("/games/{id}/join") {
	val userSession = call.sessions.get<UserSession>() ?: run {
		call.respond(HttpStatusCode.Unauthorized, "You must be logged in to create a message.")
		return@post
	}

	val id = call.parameters["id"]?.toIntOrNull() ?: run {
		call.respond(HttpStatusCode.BadRequest)
		return@post
	}

	val entityStore = database.runQuery {
		QueryDsl.from(Tables.game).where {
			Tables.game.id eq id
		}.innerJoin(Tables.userGame) {
			Tables.userGame.gameId eq Tables.game.id
		}.includeAll()
	}

	val members = entityStore[Tables.userGame]
	val game = entityStore[Tables.game].firstOrNull()

	if (game == null) {
		call.respond(HttpStatusCode.NotFound)
		return@post
	}

	if (members.size < MAX_PLAYERS) {
		runCatching {
			database.runQuery {
				QueryDsl.insert(Tables.userGame).single(UserGame(id, userSession.userId))
			}
		}.onFailure {
			if (it is UniqueConstraintException) call.respond(
				HttpStatusCode.Conflict,
				"You are already a member of this game."
			)
		}

		call.respond(HttpStatusCode.OK)
		return@post
	}

	if (waitingList.any { (id) -> id == userSession.userId }) {
		call.respond(HttpStatusCode.Conflict, "You are already in the waiting list.")
		return@post
	}

	val user = database.runQuery {
		QueryDsl.from(Tables.user).where {
			Tables.user.id eq userSession.userId
		}.first()
	}

	waitingList += user
	call.respond(HttpStatusCode.OK)
}

fun Route.members() = get("/games/{id}/members") {
	val id = call.parameters["id"]?.toIntOrNull() ?: run {
		call.respond(HttpStatusCode.BadRequest)
		return@get
	}

	val entityStore = database.runQuery {
		QueryDsl.from(Tables.userGame).where {
			Tables.userGame.gameId eq id
		}.innerJoin(Tables.user) {
			Tables.userGame.userId eq Tables.user.id
		}.includeAll()
	}

	val members = entityStore[Tables.user]

	call.respond(HttpStatusCode.OK, members)
}
