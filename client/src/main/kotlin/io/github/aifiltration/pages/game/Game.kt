package io.github.aifiltration.pages.game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.aifiltration.api.actions.logout
import io.github.aifiltration.api.plugins.FileCookiesStorage
import io.github.aifiltration.cacheAppData
import io.github.aifiltration.composables.Page
import io.github.aifiltration.storage
import io.github.aifiltration.theme.green400
import io.github.aifiltration.theme.purple700
import io.github.aifiltration.theme.red200
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@Composable
fun GamePage(
	isLoggedIn: MutableState<Boolean>,
) {
	Page {
		cacheAppData.UpdateMembersEffect()

		val scrollState = rememberLazyListState()
		Scaffold(
			topBar = {
				TopBar(cacheAppData.members.value.filter { it.id != cacheAppData.currentUser.id })
			},
			bottomBar = {
				TextArea(scrollState)
			}
		) { innerPadding ->
			Row {
				Box(modifier = Modifier.padding(innerPadding)) {
					MessageList(scrollState)

					var timeLeft by remember { mutableStateOf(0) }
					Text(
						timeLeft.toString(),
						color = Color.White,
						modifier = Modifier
							.align(Alignment.TopCenter)
							.padding(top = 10.dp),
						style = MaterialTheme.typography.body2,
					)

					LaunchedEffect(timeLeft) {
						while (true) {
							val createdInstant = cacheAppData.currentGame!!.createdAt
							val gameLength = cacheAppData.currentGame!!.length
							timeLeft =
								(gameLength - (System.currentTimeMillis() - createdInstant.toEpochMilliseconds()) / 1000).toInt()

							if (timeLeft <= 0) {
								cacheAppData.updateCurrentGame()
								cacheAppData.joinCurrentGame()
								cacheAppData.updateMessages()
								cacheAppData.updateMembers()
								cacheAppData.updateWaitingRoom()
							}
							delay(999)
						}
					}
				}

				Surface(
					color = purple700,
				) {
					Column {
						WaitingRoom()
						Column(
							horizontalAlignment = Alignment.CenterHorizontally,
							modifier = Modifier.fillMaxSize(),
							verticalArrangement = Arrangement.SpaceEvenly,
						) {
							GameButton("Play Again", green400)
							GameButton("Quit", red200) {
								runBlocking {
									logout()
								}

								FileCookiesStorage.clear()

								storage["loggedIn"] = "false"
								storage["username"] = ""
								storage["password"] = ""
								storage.remove("gameId")
								storage.save()
								isLoggedIn.value = false
							}
						}
					}
				}
			}
		}
	}
}

@Composable
fun GameButton(text: String, color: Color, onClick: () -> Unit = {}) {
	Button(
		modifier = Modifier
			.padding(16.dp)
			.width(200.dp),
		shape = MaterialTheme.shapes.medium,
		colors = buttonColors(
			backgroundColor = color,
			contentColor = Color.White,
		),
		onClick = onClick
	) {
		Text(text, style = MaterialTheme.typography.button.copy(fontSize = 28.sp))
	}
}
