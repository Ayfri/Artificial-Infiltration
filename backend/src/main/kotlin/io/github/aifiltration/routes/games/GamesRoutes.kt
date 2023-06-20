package io.github.aifiltration.routes.games

import io.github.aifiltration.routes.auth.auth
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.gamesRoutes() {
	routing {
		auth {
			cooldown()
			currentGame()
			game()
			joinGame()
			members()
		}
	}
}
