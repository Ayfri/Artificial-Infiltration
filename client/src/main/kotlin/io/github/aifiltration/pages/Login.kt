package io.github.aifiltration.pages

import androidx.compose.desktop.ui.tooling.preview.Preview
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
import kotlinx.coroutines.runBlocking

@Composable
@Preview
fun LoginPage(
	isOnLoginPageMutableState: MutableState<Boolean> = remember { mutableStateOf(true) },
) {
	Page {
		AuthBox {
			var username by rememberSaveable { mutableStateOf("") }
			var password by rememberSaveable { mutableStateOf("") }

			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.fillMaxWidth(),
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
					onValueChange = { password = it }
				)
			}

			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.fillMaxWidth(),
			) {
				AuthButton("Login", color1 = green400, color2 = green200) {
					storage["username"] = username
					storage["password"] = password
					storage["loggedIn"] = "true"
					storage.save()

					runBlocking {
						val user = login()
						user.apply(::println)
					}
				}

				Text(
					"No account? Sign up now!",
					color = MaterialTheme.colors.onPrimary,
					modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
					style = MaterialTheme.typography.subtitle1
				)

				AuthButton("Sign up", color1 = pink500, color2 = pink300) {
					isOnLoginPageMutableState.value = false
				}
			}
		}
	}
}
