package io.github.aifiltration

import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.models.Game
import io.github.aifiltration.models.game
import kotlinx.datetime.Instant
import org.komapper.core.dsl.QueryDsl

class PlayingGame {
	var id: Int = 0
		private set

	var createdAt: Instant = Instant.DISTANT_PAST
		private set

	fun getAsGame() = Game(id, GAME_DURATION, createdAt)

	suspend fun saveGame() {
		val game = database.runQuery {
			QueryDsl.insert(Tables.game).values {}.returning()
		}

		id = game.id
		createdAt = game.timestamp
	}

	override fun toString() =
		"PlayingGame(id=$id, createdAt=$createdAt)"

	companion object {
		const val GAME_DURATION = 300
	}
}

suspend fun createGame() = PlayingGame().apply {
	saveGame()
	LOGGER.debug("Created game $id.")
}
