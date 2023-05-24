package io.github.aifiltration

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import io.github.aifiltration.plugins.configureMonitoring
import io.github.aifiltration.plugins.configureRouting

fun main() {
	embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
	configureMonitoring()
	configureRouting()
}
