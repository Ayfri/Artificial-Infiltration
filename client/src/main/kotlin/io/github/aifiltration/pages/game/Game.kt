package io.github.aifiltration.pages.game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.aifiltration.composables.Page
import io.github.aifiltration.storage
import io.github.aifiltration.theme.green400
import io.github.aifiltration.theme.purple700
import io.github.aifiltration.theme.red200
import io.github.aifiltration.types.User

@Composable
fun GamePage(
	isLoggedIn: MutableState<Boolean>,
) {
	Page {
		val placeholderUsers = (1..5).map { User(it, "User $it") }
		val scrollState = rememberLazyListState()
		Scaffold(
			topBar = {
				TopBar(placeholderUsers)
			},
			bottomBar = {
				TextArea(scrollState)
			}
		) { innerPadding ->
			Row {
				Box(modifier = Modifier.padding(innerPadding)) {
					MessageList(scrollState)
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
								storage["loggedIn"] = "false"
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
