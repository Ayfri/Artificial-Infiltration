package io.github.aifiltration.models

import io.github.aifiltration.now
import kotlinx.datetime.LocalDateTime
import org.komapper.annotation.*


@KomapperEntity
@KomapperTable("games")
data class Game(
	@KomapperAutoIncrement
	@KomapperId
	val id: Int = 0,
	@KomapperCreatedAt
	val timestamp: LocalDateTime = LocalDateTime.now(),
)
