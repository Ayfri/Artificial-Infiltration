package io.github.aifiltration.pages.game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.aifiltration.cacheAppData
import io.github.aifiltration.composables.MessageBox
import io.github.aifiltration.composables.UserAvatar
import io.github.aifiltration.types.Message

@Composable
fun MessageList(scrollState: LazyListState) {
	cacheAppData.UpdateMessagesEffect()

	LazyColumn(
		modifier = Modifier.fillMaxWidth(.75f).padding(horizontal = 16.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp),
		state = scrollState,
		reverseLayout = true
	) {
		item {
			Spacer(Modifier.padding(4.dp))
		}
		items(cacheAppData.messages.value.sortedByDescending { it.timestamp }) {
			Message(it)
		}
		item {
			Spacer(Modifier)
		}
	}
}

@Composable
fun Message(message: Message) {
	Row {
		UserAvatar(message.author)
		MessageBox(message)
	}
}
