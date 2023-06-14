package io.github.aifiltration.pages.game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.aifiltration.composables.MessageBox
import io.github.aifiltration.composables.UserAvatar
import io.github.aifiltration.types.Message
import io.github.aifiltration.types.User

@Composable
fun MessageList() {
	val placeholderMessages = (1..15).map {
		Message(it, "Message $it", User(0, "Username"), 0)
	}.sortedBy { it.timestamp }

	val scrollState = rememberLazyListState()
	LazyColumn(
		modifier = Modifier.fillMaxWidth(.75f).padding(horizontal = 16.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp),
		state = scrollState,
	) {
		item {
			Spacer(Modifier)
		}
		items(placeholderMessages) {
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
