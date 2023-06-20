@file:Suppress("LoggingStringTemplateAsArgument")

package io.github.aifiltration

import io.github.aifiltration.ai.AI_ID
import io.github.aifiltration.ai.chatCompletionRequest
import io.github.aifiltration.ai.queryChatCompletionMessages
import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.models.*
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
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.operator.desc
import org.komapper.core.dsl.query.andThen
import org.komapper.core.dsl.query.map
import kotlin.time.Duration.Companion.seconds

const val MAX_PLAYERS = 5

lateinit var currentGame: PlayingGame
var gameCooldown = Instant.DISTANT_PAST
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
			database.runQuery {
				QueryDsl.insert(Tables.userGame).single(UserGame(gameId = currentGame.id, userId = AI_ID))
			}

			delay(PlayingGame.GAME_DURATION * 1000L)
			gameCooldown = Clock.System.now() + PlayingGame.COOLDOWN_DURATION.seconds

			val votes = database.runQuery {
				QueryDsl.from(Tables.vote).where {
					Tables.vote.gameId eq currentGame.id
					Tables.vote.targetId eq AI_ID
				}.orderBy(Tables.vote.createdAt.desc())
			}

			val queries = votes.indices.map { index ->
				QueryDsl.update(Tables.userGame).include(Tables.userGame.points).set {
					Tables.userGame.points eq index + 1
				}.where {
					Tables.userGame.gameId eq currentGame.id
					Tables.userGame.userId eq votes[index].authorId
				}.map {}
			}

			if (queries.isNotEmpty()) database.runQuery {
				queries.reduce { acc, query -> acc.andThen(query) }
			}

			delay(PlayingGame.COOLDOWN_DURATION * 1000L)
			gameCooldown = Instant.DISTANT_PAST
		}
	}

	gameCreationScope.launch {
		while (true) {
			LOGGER.debug("AI message delay: ${PlayingGame.AI_MESSAGE_DELAY}s")
			delay(PlayingGame.AI_MESSAGE_DELAY * 1000L)

			val members = database.runQuery {
				QueryDsl.from(Tables.userGame).where {
					Tables.userGame.gameId eq currentGame.id
				}
			}

			if (members.size < 3) continue

			val messages = database.runQuery {
				QueryDsl.from(Tables.message).where {
					Tables.message.gameId eq currentGame.id
				}
			}.sortedBy { it.timestamp }

			if (messages.isNotEmpty()) {
				val lastMessage = messages.last()
				val timeDifferenceBetweenLast = (Clock.System.now() - lastMessage.timestamp).inWholeSeconds
				if (timeDifferenceBetweenLast > 30 || lastMessage.authorId == AI_ID) continue
			}

			runCatching {
				val chatCompletionMessages = queryChatCompletionMessages(currentGame.id)
				val aiMessage = chatCompletionRequest(chatCompletionMessages)!!

				database.runQuery {
					QueryDsl.insert(Tables.message).single(
						Message(
							content = aiMessage.take(140),
							authorId = AI_ID,
							gameId = currentGame.id
						)
					)
				}
			}
		}
	}
}
