package io.github.aifiltration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import io.github.aifiltration.api.actions.currentGame
import io.github.aifiltration.pages.LoginPage
import io.github.aifiltration.pages.SignUpPage
import io.github.aifiltration.pages.game.GamePage
import io.github.aifiltration.storage.CacheAppData
import io.github.aifiltration.storage.Storage
import io.github.aifiltration.theme.AITheme
import io.github.aifiltration.types.User
import kotlinx.coroutines.runBlocking

val cacheAppData by mutableStateOf(CacheAppData(User(0, "User 0")))
val storage by mutableStateOf(Storage("config.json"))


fun main() = singleWindowApplication(
	title = "Artificial Infiltration",
	state = WindowState(width = 1600.dp, height = 900.dp, position = WindowPosition.Aligned(Alignment.Center)),
) {
	AITheme {
		val isLoggedIn = remember { mutableStateOf(storage["loggedIn"] == "true") }
		if (isLoggedIn.value) {
			runCatching {
				runBlocking {
					storage["gameId"] = currentGame().getOrThrow().id.toString()
				}
			}.onFailure {
				storage.remove("gameId")
				storage.save()
			}

			GamePage(isLoggedIn)
			return@AITheme
		}

		val isLoginPage = remember { mutableStateOf(true) }
		if (isLoginPage.value) LoginPage(isLoginPage, isLoggedIn)
		else SignUpPage(isLoginPage)

		runCatching {
			runBlocking {
				storage.load()
				if (storage["loggedIn"] == "true") cacheAppData.updateCurrentUser()
			}
		}.onFailure {
			storage["loggedIn"] = "false"
			storage.save()
		}
	}
}
