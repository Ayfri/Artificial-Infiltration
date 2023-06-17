package io.github.aifiltration.storage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import io.github.aifiltration.api.actions.*
import io.github.aifiltration.types.Game
import io.github.aifiltration.types.Message
import io.github.aifiltration.types.User
import kotlinx.coroutines.delay

data class CacheAppData(
	var currentGame: Game? = null,
	var currentUser: User = User(0, "User 0"),
	var members: MutableState<List<User>> = mutableStateOf(listOf()),
	var messages: MutableState<List<Message>> = mutableStateOf(listOf()),
	var waitingRoom: MutableState<List<User>> = mutableStateOf(listOf()),
) {
	suspend fun joinCurrentGame() = joinGame(currentGame!!.id)

	suspend fun updateCurrentGame() {
		currentGame = currentGame().getOrThrow()
	}

	suspend fun updateCurrentUser() {
		currentUser = me().getOrThrow()
	}

	suspend fun updateMembers() {
		members.value = getMembers(currentGame!!.id).getOrThrow()
	}

	suspend fun updateMessages() {
		messages.value = getMessages(currentGame!!.id).getOrThrow()
	}

	suspend fun updateWaitingRoom() {
		waitingRoom.value = waitingRoom().getOrThrow()
	}

	@Composable
	fun UpdateMembersEffect() = LaunchedEffect(members) {
		while (true) {
			updateMembers()
			delay(DELAY_MEMBERS)
		}
	}

	@Composable
	fun UpdateMessagesEffect() = LaunchedEffect(messages) {
		while (true) {
			updateMessages()
			delay(DELAY_MESSAGES)
		}
	}

	@Composable
	fun UpdateWaitingRoomEffect() = LaunchedEffect(waitingRoom) {
		while (true) {
			updateWaitingRoom()
			delay(DELAY_WAITING_ROOM)
		}
	}

	companion object {
		const val DELAY_MEMBERS = 5000L
		const val DELAY_MESSAGES = 5000L
		const val DELAY_WAITING_ROOM = 10000L
	}
}
