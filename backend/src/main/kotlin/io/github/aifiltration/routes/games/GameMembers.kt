package io.github.aifiltration.routes.games

import io.github.aifiltration.LOGGER
import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.models.UserGame
import io.github.aifiltration.models.game
import io.github.aifiltration.models.userGame
import io.github.aifiltration.plugins.UserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.komapper.core.UniqueConstraintException
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.firstOrNull

fun Route.joinGame() = post("/games/{id}/join") {
	val userSession = call.sessions.get<UserSession>() ?: run {
		call.respond(HttpStatusCode.Unauthorized, "You must be logged in to create a message.")
		return@post
	}

	val id = call.parameters["id"]?.toIntOrNull() ?: run {
		call.respond(HttpStatusCode.BadRequest)
		return@post
	}

	val game = database.runQuery {
		QueryDsl.from(Tables.game).where {
			Tables.game.id eq id
		}.firstOrNull()
	}

	if (game == null) {
		call.respond(HttpStatusCode.NotFound)
		return@post
	}

	runCatching {
		database.runQuery {
			QueryDsl.insert(Tables.userGame).single(UserGame(id, userSession.userId))
		}
	}.onSuccess {
		call.respond(HttpStatusCode.OK)
	}.onFailure {
		if (it is UniqueConstraintException) call.respond(
			HttpStatusCode.BadRequest,
			"You are already a member of this game."
		)
		else {
			call.respond(HttpStatusCode.InternalServerError)
			LOGGER.error("Failed to join game.", it)
		}
	}
}
