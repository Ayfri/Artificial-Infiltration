package io.github.aifiltration.routes.messages

import io.github.aifiltration.routes.auth.auth
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.messagesRoutes() {
	routing {
		auth {
			createMessage()
			messages()
		}
	}
}
