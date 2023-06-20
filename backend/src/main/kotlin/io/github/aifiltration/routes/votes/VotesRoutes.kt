package io.github.aifiltration.routes.votes

import io.github.aifiltration.routes.auth.auth
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.votesRoutes() {
	routing {
		auth {
			vote()
			votes()
		}
	}
}
