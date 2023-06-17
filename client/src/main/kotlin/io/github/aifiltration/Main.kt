package io.github.aifiltration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import io.github.aifiltration.api.actions.login
import io.github.aifiltration.pages.LoginPage
import io.github.aifiltration.pages.SignUpPage
import io.github.aifiltration.pages.game.GamePage
import io.github.aifiltration.storage.CacheAppData
import io.github.aifiltration.storage.Storage
import io.github.aifiltration.theme.AITheme
import kotlinx.coroutines.runBlocking

val cacheAppData by mutableStateOf(CacheAppData())
val storage by mutableStateOf(Storage("config.json")).apply { value.load() }

fun main() = singleWindowApplication(
	title = "Artificial Infiltration",
	state = WindowState(width = 1600.dp, height = 900.dp, position = WindowPosition.Aligned(Alignment.Center)),
) {
	AITheme {
		val isLoggedIn = mutableStateOf(storage["loggedIn"] == "true" && !storage["username"].isNullOrBlank())

		if (isLoggedIn.value) {
			runBlocking {
				cacheAppData.updateCurrentGame()
				if (cacheAppData.currentUser.id == 0) cacheAppData.updateCurrentUser()
				cacheAppData.joinCurrentGame()
			}.onFailure {
				cacheAppData.currentGame = null
				isLoggedIn.value = false
			}

			GamePage(isLoggedIn)
			return@AITheme
		}

		val isLoginPage = remember { mutableStateOf(true) }
		if (isLoginPage.value) LoginPage(isLoginPage, isLoggedIn)
		else SignUpPage(isLoginPage)

		runCatching {
			runBlocking {
				if (storage["loggedIn"] == "true") {
					login()
					cacheAppData.updateCurrentUser()
					isLoggedIn.value = true
				}
			}
		}.onFailure {
			storage["loggedIn"] = "false"
			storage.save()
		}
	}
}
