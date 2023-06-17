package io.github.aifiltration.storage

import io.github.aifiltration.api.actions.currentGame
import io.github.aifiltration.api.actions.joinGame
import io.github.aifiltration.api.actions.me
import io.github.aifiltration.types.Game
import io.github.aifiltration.types.User

data class CacheAppData(
	var currentGame: Game? = null,
	var currentUser: User = User(0, "User 0"),
) {
	suspend fun updateCurrentUser() {
		currentUser = me().getOrThrow()
	}

	suspend fun joinCurrentGame() = joinGame(currentGame!!.id)

	suspend fun updateCurrentGame() {
		currentGame = currentGame().getOrThrow()
	}
}
