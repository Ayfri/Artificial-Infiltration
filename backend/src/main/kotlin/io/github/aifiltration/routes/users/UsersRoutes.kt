package io.github.aifiltration.routes.users

import io.github.aifiltration.routes.auth.auth
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.usersRoutes() {
	routing {
		auth {
			me()
			waitingList()
		}
	}
}
