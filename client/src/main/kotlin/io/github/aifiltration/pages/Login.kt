package io.github.aifiltration.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.github.aifiltration.api.actions.login
import io.github.aifiltration.composables.*
import io.github.aifiltration.storage
import io.github.aifiltration.theme.green200
import io.github.aifiltration.theme.green400
import io.github.aifiltration.theme.pink300
import io.github.aifiltration.theme.pink500
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

@Composable
fun LoginPage(
	isOnLoginPage: MutableState<Boolean>,
	isLoggedIn: MutableState<Boolean>,
) {
	Page {
		AuthBox {
			var username by rememberSaveable { mutableStateOf("") }
			var password by rememberSaveable { mutableStateOf("") }

			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.fillMaxWidth().onEnterKeyPressed {
					executeLogin(username, password, isLoggedIn)
				},
			) {
				Title("Login", color = MaterialTheme.colors.onPrimary, modifier = Modifier.padding(top = 16.dp))
				Spacer(modifier = Modifier.padding(top = 60.dp))

				AuthInput(
					value = username,
					placeholder = "Username",
					icon = { Icon(Icons.Filled.Person, contentDescription = "Username") },
					onValueChange = { username = it }
				)

				AuthInput(
					value = password,
					placeholder = "Password",
					keyboardType = KeyboardType.Password,
					icon = { Icon(Icons.Filled.Lock, contentDescription = "Password") },
					onValueChange = { password = it },
				)
			}

			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.fillMaxWidth(),
			) {
				AuthButton("Login", color1 = green400, color2 = green200) {
					executeLogin(username, password, isLoggedIn)
				}

				Text(
					"No account? Sign up now!",
					color = MaterialTheme.colors.onPrimary,
					modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
					style = MaterialTheme.typography.subtitle1
				)

				AuthButton("Sign up", color1 = pink500, color2 = pink300) {
					isOnLoginPage.value = false
				}
			}
		}
	}
}

private fun executeLogin(
	username: String,
	password: String,
	isLoggedIn: MutableState<Boolean>,
) {
	storage["username"] = username
	storage["password"] = password
	storage.save()

	runCatching {
		runBlocking {
			val response = login()
			if (response.isSuccess && response.getOrNull()?.status == HttpStatusCode.OK) {
				storage["loggedIn"] = "true"
				storage.save()

				isLoggedIn.value = true
			} else {
				storage["loggedIn"] = "false"
				storage.save()
			}
		}
	}
}
