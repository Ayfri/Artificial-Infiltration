package io.github.aifiltration.pages.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.aifiltration.api.actions.createMessage
import io.github.aifiltration.cacheAppData
import io.github.aifiltration.composables.UserAvatar
import io.github.aifiltration.composables.moveFocusOnTab
import io.github.aifiltration.composables.onEnterKeyPressed
import io.github.aifiltration.pages.GAME_WIDTH_RATIO
import io.github.aifiltration.theme.purple700
import io.github.aifiltration.theme.purple800
import io.github.aifiltration.theme.purple900
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val MAX_MESSAGE_LENGTH = 140
const val COOLDOWN = 3
var cooldown by mutableStateOf(0)

@Composable
fun TextArea(scrollState: LazyListState) {
	var text by remember { mutableStateOf("") }
	val scope = rememberCoroutineScope()
	val focusRequester = remember { FocusRequester() }

	Row(
		modifier = Modifier
			.fillMaxWidth(GAME_WIDTH_RATIO)
			.background(purple800)
			.padding(horizontal = 16.dp, vertical = 24.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		UserAvatar(cacheAppData.currentUser)
		TextField(
			value = text,
			enabled = cooldown == 0,
			onValueChange = {
				if (it.length <= MAX_MESSAGE_LENGTH) text = it.replace(Regex("[\n\t\r]"), "")
			},
			placeholder = {
				if (cooldown > 0) Text("($cooldown) Type something sussy...", style = MaterialTheme.typography.body1)
				else Text("Type something sussy...", style = MaterialTheme.typography.body1)
			},
			shape = MaterialTheme.shapes.large,
			colors = TextFieldDefaults.textFieldColors(
				backgroundColor = purple900,
				disabledIndicatorColor = Color.Transparent,
				errorIndicatorColor = Color.Transparent,
				focusedIndicatorColor = Color.Transparent,
				placeholderColor = MaterialTheme.colors.primary,
				textColor = Color.White,
				unfocusedIndicatorColor = Color.Transparent,
			),
			maxLines = 3,
			modifier = Modifier
				.fillMaxWidth(.92f)
				.moveFocusOnTab()
				.focusRequester(focusRequester)
				.onEnterKeyPressed {
					sendMessage(text, focusRequester, scrollState, scope)
					text = ""
				}
		)

		IconButton(
			enabled = cooldown == 0,
			modifier = Modifier
				.background(purple900, shape = CircleShape)
				.size(60.dp),
			onClick = {
				sendMessage(text, focusRequester, scrollState, scope)
				text = ""
			}
		) {
			Icon(
				Icons.Filled.Send,
				contentDescription = "Send",
				modifier = Modifier.size(32.dp),
				tint = if (cooldown == 0) MaterialTheme.colors.primary else purple700
			)
		}
	}
}

private fun sendMessage(
	text: String,
	focusRequester: FocusRequester,
	scrollState: LazyListState,
	coroutineScope: CoroutineScope,
) =
	coroutineScope.launch {
		cooldown = COOLDOWN
		coroutineScope.launch {
			while (cooldown > 0) {
				delay(1000)
				cooldown--
			}

			delay(20)

			focusRequester.requestFocus()
		}

		val gameId = cacheAppData.currentGame!!.id
		val response = createMessage(gameId, text)
		if (response.isSuccess) cacheAppData.updateMessages()
		scrollState.animateScrollToItem(0)
	}
