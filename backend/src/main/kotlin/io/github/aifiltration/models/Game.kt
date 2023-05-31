package io.github.aifiltration.models

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import org.komapper.annotation.*


@KomapperEntity
@KomapperTable("games")
@Serializable
data class Game(
	@KomapperAutoIncrement
	@KomapperId
	val id: Int = 0,
	@KomapperCreatedAt
	val timestamp: Instant = Clock.System.now(),
)
