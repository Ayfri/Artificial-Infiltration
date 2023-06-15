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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import io.github.aifiltration.LOGGER
import io.github.aifiltration.api.actions.createMessage
import io.github.aifiltration.api.actions.getMessages
import io.github.aifiltration.cacheAppData
import io.github.aifiltration.composables.UserAvatar
import io.github.aifiltration.composables.moveFocusOnTab
import io.github.aifiltration.storage
import io.github.aifiltration.theme.purple800
import io.github.aifiltration.theme.purple900
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val MAX_MESSAGE_LENGTH = 140

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextArea(scrollState: LazyListState) {
	var text by remember { mutableStateOf("") }
	val scope = rememberCoroutineScope()

	Row(
		modifier = Modifier
			.fillMaxWidth(.75f)
			.background(purple800)
			.padding(horizontal = 16.dp, vertical = 24.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		UserAvatar(cacheAppData.currentUser)
		TextField(
			value = text,
			onValueChange = {
				if (it.length <= MAX_MESSAGE_LENGTH) text = it.replace(Regex("[\n\t\r]"), "")
			},
			placeholder = {
				Text("Type something sussy...", style = MaterialTheme.typography.body1)
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
				.onKeyEvent {
					if (it.key == Key.Enter || it.key == Key.NumPadEnter) {
						sendMessage(text, scrollState, scope)
						text = ""
						true
					} else false
				}
		)

		IconButton(
			onClick = {
				sendMessage(text, scrollState, scope)
				text = ""
			},
			modifier = Modifier
				.background(purple900, shape = CircleShape)
				.size(60.dp)
		) {
			Icon(
				Icons.Filled.Send,
				contentDescription = "Send",
				modifier = Modifier.size(32.dp),
				tint = MaterialTheme.colors.primary
			)
		}
	}
}

private fun sendMessage(text: String, scrollState: LazyListState, coroutineScope: CoroutineScope) =
	coroutineScope.launch {
		val gameId = storage["gameId"]!!.toInt()
		val response = createMessage(gameId, text).onFailure {
			LOGGER.error("Failed to send message", it)
		}
		if (response.isSuccess) messages = getMessages(gameId).getOrThrow()
		scrollState.animateScrollToItem(0)
	}
