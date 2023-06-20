package io.github.aifiltration.storage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import io.github.aifiltration.LOGGER
import io.github.aifiltration.api.actions.*
import io.github.aifiltration.api.plugins.FileCookiesStorage
import io.github.aifiltration.types.Game
import io.github.aifiltration.types.Message
import io.github.aifiltration.types.User
import io.github.aifiltration.types.Vote
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.coroutines.delay
import kotlinx.datetime.Instant

data class CacheAppData(
	var cooldown: MutableState<Instant> = mutableStateOf(Instant.DISTANT_PAST),
	var currentGame: Game? = null,
	var currentGameFinished: MutableState<Boolean> = mutableStateOf(false),
	var currentUser: User = User(0, "User 0", 0),
	var hasVoted: MutableState<Boolean> = mutableStateOf(false),
	var isOnLeaderboard: MutableState<Boolean> = mutableStateOf(false),
	var members: MutableState<List<User>> = mutableStateOf(listOf()),
	var messages: MutableState<List<Message>> = mutableStateOf(listOf()),
	var votes: MutableState<List<Vote>> = mutableStateOf(listOf()),
	var waitingRoom: MutableState<List<User>> = mutableStateOf(listOf()),
) {
	suspend fun joinCurrentGame() = joinGame(currentGame!!.id)

	suspend fun updateCooldown() {
		cooldown.value = cooldown().getOrThrow().also {
			LOGGER.info("Cooldown updated to $it")
		}
	}

	suspend fun updateCurrentGame() {
		currentGame = currentGame().getOrThrow()
	}

	suspend fun updateCurrentUser() {
		val user = me().getOrNull()
		if (user == null) {
			FileCookiesStorage.clear()
			return
		}
		currentUser = user
	}

	suspend fun updateMembers() {
		members.value = getMembers(currentGame!!.id).getOrThrow()
	}

	suspend fun updateMessages() {
		messages.value = getMessages(currentGame!!.id).getOrThrow()
	}

	suspend fun updateVotes() {
		val response = getVotes(currentGame!!.id).getOrThrow()

		runCatching {
			votes.value = response.body<List<Vote>>()
		}.onFailure {
			LOGGER.error("Failed to get votes : ${response.status}: ${response.bodyAsText()}")
		}
	}

	suspend fun updateWaitingRoom() {
		waitingRoom.value = waitingRoom().getOrThrow()
	}

	@Composable
	fun UpdateMembersEffect() = LaunchedEffect(members) {
		while (true) {
			if (!currentGameFinished.value) updateMembers()
			delay(DELAY_MEMBERS)
		}
	}

	@Composable
	fun UpdateMessagesEffect() = LaunchedEffect(messages) {
		while (true) {
			if (!currentGameFinished.value) updateMessages()
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
