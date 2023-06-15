package io.github.aifiltration.routes.games

import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.models.game
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.firstOrNull


fun Route.game() = get("/games/{id}") {
	val id = call.parameters["id"]?.toIntOrNull() ?: run {
		call.respond(HttpStatusCode.BadRequest)
		return@get
	}

	val game = database.runQuery {
		QueryDsl.from(Tables.game).where {
			Tables.game.id eq id
		}.firstOrNull()
	}

	if (game == null) {
		call.respond(HttpStatusCode.NotFound)
		return@get
	}

	call.respond(game)
}
