package io.github.aifiltration.storage

import io.github.aifiltration.api.actions.me
import io.github.aifiltration.types.User

data class CacheAppData(
	var currentUser: User,
) {
	suspend fun updateCurrentUser() {
		currentUser = me().getOrThrow()
	}
}
