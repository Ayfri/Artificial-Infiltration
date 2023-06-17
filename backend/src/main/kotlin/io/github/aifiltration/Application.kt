package io.github.aifiltration

import io.github.aifiltration.models.User
import io.github.aifiltration.plugins.configureAuth
import io.github.aifiltration.plugins.configureContentNegotiation
import io.github.aifiltration.plugins.configureMonitoring
import io.github.aifiltration.plugins.configureSessions
import io.github.aifiltration.routes.configureRouting
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val MAX_PLAYERS = 5

lateinit var currentGame: PlayingGame
val waitingList = mutableSetOf<User>()

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

	val gameCreationScope = CoroutineScope(Dispatchers.Default)
	gameCreationScope.launch {
		while (true) {
			currentGame = createGame()
			delay(PlayingGame.GAME_DURATION * 1000L)
		}
	}
}
