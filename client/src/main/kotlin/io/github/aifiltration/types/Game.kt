package io.github.aifiltration.types

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Game(
	val id: Int,
	@SerialName("timestamp")
	val createdAt: Instant,
)
