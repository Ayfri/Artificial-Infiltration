package io.github.aifiltration.models

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.komapper.annotation.*


@KomapperEntity
@KomapperTable("games")
data class Game(
	@KomapperAutoIncrement
	@KomapperId
	val id: Int = 0,
	@KomapperCreatedAt
	val timestamp: Instant = Clock.System.now(),
)
