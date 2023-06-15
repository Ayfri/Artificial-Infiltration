package io.github.aifiltration.models

import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable

@KomapperEntity
@KomapperTable("user_games")
data class UserGame(
	@KomapperId(virtual = true)
	val gameId: Int,
	@KomapperId(virtual = true)
	val userId: Int,
	val points: Int = 0,
)
