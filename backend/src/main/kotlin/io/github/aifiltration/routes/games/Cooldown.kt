package io.github.aifiltration.routes.games

import io.github.aifiltration.gameCooldown
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cooldown() = get("/cooldown") {
	call.respond(HttpStatusCode.OK, gameCooldown)
}
