package io.github.aifiltration.storage

import androidx.compose.runtime.Immutable
import io.github.aifiltration.api.actions.me
import io.github.aifiltration.types.User

@Immutable
data class CacheAppData(
	val currentUser: User,
) {
	suspend fun updateCurrentUser() = copy(currentUser = me().getOrThrow())
	fun updateCurrentUser(user: User) = copy(currentUser = user)
}
