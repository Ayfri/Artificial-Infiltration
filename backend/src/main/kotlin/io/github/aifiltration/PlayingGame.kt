package io.github.aifiltration

import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.models.*
import kotlinx.datetime.Instant
import org.komapper.core.dsl.QueryDsl

class PlayingGame {
	var id: Int = 0
		private set

	var createdAt: Instant = Instant.DISTANT_PAST
		private set

	var members = emptyList<User>()
		private set

	var messages = emptyList<Message>()
		private set

	var votes = emptyList<Vote>()
		private set

	val isFinished
		get() = votes.size == members.size

	fun getAsGame() = Game(id, createdAt)

	suspend fun saveGame() {
		val game = database.runQuery {
			QueryDsl.insert(Tables.game).values {}.returning()
		}

		id = game.id
		createdAt = game.timestamp
	}

	suspend fun addMember(user: User) {
		database.runQuery {
			QueryDsl.insert(Tables.userGame).values {
				it.gameId eq id
				it.userId eq user.id
			}
		}

		members += user
	}

	suspend fun addMessage(message: Message, author: User) {
		database.runQuery {
			QueryDsl.insert(Tables.message).values {
				it.gameId eq id
				it.authorId eq author.id
				it.content eq message.content
			}
		}

		messages += message
	}

	suspend fun addVote(vote: Vote) {
		database.runQuery {
			QueryDsl.insert(Tables.vote).values {
				it.gameId eq id
				it.authorId eq vote.authorId
				it.targetId eq vote.targetId
			}
		}

		votes += vote
	}

	override fun toString() =
		"PlayingGame(id=$id, createdAt=$createdAt, members=$members, messages=$messages, votes=$votes)"
}

suspend fun createGame() = PlayingGame().apply {
	saveGame()
	LOGGER.debug("Created game $id.")
}
