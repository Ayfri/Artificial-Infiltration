package io.github.aifiltration.routes

import io.github.aifiltration.routes.auth.authentificationRoutes
import io.ktor.server.application.*

fun Application.configureRouting() {
	authentificationRoutes()
}
