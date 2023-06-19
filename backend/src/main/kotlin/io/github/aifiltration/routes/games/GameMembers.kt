package io.github.aifiltration.routes.games

import io.github.aifiltration.*
import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.models.User
import io.github.aifiltration.models.UserGame
import io.github.aifiltration.models.user
import io.github.aifiltration.models.userGame
import io.github.aifiltration.plugins.UserSession
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
		call.respond(HttpStatusCode.Unauthorized, "You must be logged in to join the game.")
		return@post
	}

	val id = call.parameters["id"]?.toIntOrNull() ?: run {
		call.respond(HttpStatusCode.BadRequest)
		return@post
	}

	if (currentGame.id != id) {
		call.respond(HttpStatusCode.Forbidden, "You can only join the current game.")
		return@post
	}

	val members = database.runQuery {
		QueryDsl.from(Tables.userGame).where {
			Tables.userGame.gameId eq id
		}
	}

	val isMember = members.any { it.userId == userSession.userId }
	val isWaiting = waitingList.any { (id) -> id == userSession.userId }

	if (isMember || isWaiting) {
		call.respond(HttpStatusCode.Conflict, "You are already a member of this game.")
		return@post
	}

	if (members.size < MAX_PLAYERS) {
		runCatching {
			database.runQuery {
				QueryDsl.insert(Tables.userGame).single(UserGame(gameId = id, userId = userSession.userId))
			}
		}.onFailure {
			if (it is UniqueConstraintException) call.respond(
				HttpStatusCode.Conflict,
				"You are already a member of this game."
			)
			else call.respond(HttpStatusCode.InternalServerError)
		}

		call.respond(HttpStatusCode.OK, false)
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
	call.respond(HttpStatusCode.OK, true)
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

	val members = entityStore[Tables.user].mapIndexed { index, user ->
		User(user.id, usedNames.getOrPut(id) { anonymousNames[index] })
	}

	call.respond(HttpStatusCode.OK, members)
}
