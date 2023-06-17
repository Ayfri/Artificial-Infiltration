package io.github.aifiltration.routes.users

import io.github.aifiltration.waitingList
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.waitingList() = get("/waiting-list") { call.respond(HttpStatusCode.OK, waitingList) }
