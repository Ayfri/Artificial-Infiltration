package io.github.aifiltration.models

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import org.komapper.annotation.KomapperAutoIncrement
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable

@KomapperEntity
@KomapperTable("messages")
@Serializable
data class Message(
	@KomapperAutoIncrement
	@KomapperId
	val id: Int = 0,
	val content: String,
	val authorId: Int,
	val gameId: Int,
	val timestamp: Instant = Clock.System.now(),
)
