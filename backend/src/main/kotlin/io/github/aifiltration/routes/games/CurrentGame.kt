package io.github.aifiltration.routes.games

import io.github.aifiltration.currentGame
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.currentGame() {
	get("/games/current") {
		call.respond(currentGame.getAsGame())
	}
}
