package io.github.aifiltration

import io.github.aifiltration.plugins.configureAuth
import io.github.aifiltration.plugins.configureContentNegotiation
import io.github.aifiltration.plugins.configureMonitoring
import io.github.aifiltration.plugins.configureSessions
import io.github.aifiltration.routes.configureRouting
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
	embeddedServer(
		factory = CIO,
		port = 8080,
		host = "0.0.0.0",
		module = Application::module,
		watchPaths = listOf("backend"),
	).start(wait = true)
}

fun Application.module() {
	configureAuth()
	configureContentNegotiation()
	configureMonitoring()
	configureRouting()
	configureSessions()
}
