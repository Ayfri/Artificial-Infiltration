package io.github.aifiltration.pages.game

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.defaultScrollbarStyle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.aifiltration.cacheAppData
import io.github.aifiltration.composables.UserAvatar
import io.github.aifiltration.theme.purple600
import io.github.aifiltration.types.User

@Composable
@Preview
fun WaitingRoom() {
	cacheAppData.UpdateWaitingRoomEffect()

	Column {
		Text(
			"Waiting room",
			color = MaterialTheme.colors.primaryVariant,
			modifier = Modifier.fillMaxWidth(),
			style = MaterialTheme.typography.h3,
			textAlign = TextAlign.Center
		)
		val scrollState = rememberLazyListState()
		Box(
			modifier = Modifier.fillMaxHeight(.7f).padding(4.dp),
		) {
			LazyColumn(
				state = scrollState,
				modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
			) {
				items(cacheAppData.waitingRoom.value) {
					WaitingMember(it)
				}
			}

			VerticalScrollbar(
				modifier = Modifier.align(Alignment.TopEnd),
				adapter = rememberScrollbarAdapter(scrollState),
				style = defaultScrollbarStyle().copy(
					thickness = 10.dp,
					hoverColor = MaterialTheme.colors.primaryVariant,
					unhoverColor = MaterialTheme.colors.primary,
					shape = MaterialTheme.shapes.small
				)
			)
		}
	}
}

@Composable
fun WaitingMember(user: User) {
	Card(
		modifier = Modifier
			.padding(8.dp)
			.fillMaxWidth(),
		backgroundColor = purple600,
		border = BorderStroke(2.dp, MaterialTheme.colors.background),
	) {
		Row(
			modifier = Modifier.padding(8.dp),
			horizontalArrangement = Arrangement.spacedBy(12.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			UserAvatar(user)
			Text(user.username)
		}
	}
}
