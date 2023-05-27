package io.github.aifiltration.models

import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperTable

@KomapperEntity
@KomapperTable("user_games")
data class UserGame(
	val userId: Int,
	val gameId: Int,
)
