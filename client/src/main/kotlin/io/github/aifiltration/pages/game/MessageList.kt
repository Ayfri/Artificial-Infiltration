package io.github.aifiltration.pages.game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.aifiltration.api.actions.getMessages
import io.github.aifiltration.composables.MessageBox
import io.github.aifiltration.composables.UserAvatar
import io.github.aifiltration.storage
import io.github.aifiltration.types.Message
import kotlinx.coroutines.delay

var messages by mutableStateOf(listOf<Message>())

@Composable
fun MessageList() {
	LaunchedEffect(messages) {
		while (true) {
			messages = getMessages(storage["gameId"]!!.toInt()).getOrThrow()
			delay(20000)
		}
	}

	val scrollState = rememberLazyListState()
	LazyColumn(
		modifier = Modifier.fillMaxWidth(.75f).padding(horizontal = 16.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp),
		state = scrollState,
	) {
		item {
			Spacer(Modifier)
		}
		items(messages) {
			Message(it)
		}
		item {
			Spacer(Modifier.padding(12.dp))
		}
	}
}

@Composable
fun Message(message: Message) {
	Row {
		UserAvatar(message.author)
		MessageBox(message.content)
	}
}
