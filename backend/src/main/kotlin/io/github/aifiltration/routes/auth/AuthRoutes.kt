package io.github.aifiltration.routes.auth

import io.github.aifiltration.plugins.AUTH_NAME
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Routing.auth(routing: Route.() -> Unit) {
	authenticate(AUTH_NAME) {
		routing()
	}
}

fun Application.authentificationRoutes() {
	routing {
		register()

		auth {
			login()
			logout()
		}
	}
}
