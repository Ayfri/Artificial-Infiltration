package io.github.aifiltration.types

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Message(
	val id: Int = 0,
	val content: String,
	val author: User,
	val gameId: Int,
	val timestamp: Instant = Clock.System.now(),
)
