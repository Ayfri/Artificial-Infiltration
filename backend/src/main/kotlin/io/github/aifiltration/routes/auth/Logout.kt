package io.github.aifiltration.routes.auth

import io.github.aifiltration.plugins.UserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.logout() = post("/logout") {
	call.sessions.clear<UserSession>()
	call.respond(HttpStatusCode.OK)
}
