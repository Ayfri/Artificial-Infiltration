package io.github.aifiltration.routes

import io.github.aifiltration.routes.auth.authentificationRoutes
import io.github.aifiltration.routes.users.usersRoutes
import io.ktor.server.application.*

fun Application.configureRouting() {
	authentificationRoutes()
	usersRoutes()
}
