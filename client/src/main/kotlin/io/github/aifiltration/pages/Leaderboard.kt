package io.github.aifiltration.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.West
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.aifiltration.api.actions.leaderboard
import io.github.aifiltration.composables.Page
import io.github.aifiltration.theme.purple200
import io.github.aifiltration.theme.purple400
import io.github.aifiltration.theme.purple450
import io.github.aifiltration.theme.red200
import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardEntry(
	val gamePlayed: Int,
	val points: Int,
	val rank: Int,
	val username: String,
)

var leaderboardList by mutableStateOf(listOf<LeaderboardEntry>())

@Composable
fun LeaderboardPage(isOnLeaderboard: MutableState<Boolean>) {
	LaunchedEffect(leaderboardList) {
		leaderboardList = leaderboard().getOrDefault(listOf())
	}

	Page {
		BackButton(isOnLeaderboard)

		Box(
			contentAlignment = Alignment.TopCenter,
			modifier = Modifier.fillMaxSize().padding(top = 150.dp),
		) {
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.fillMaxWidth(.85f),
				verticalArrangement = Arrangement.spacedBy(12.dp),
			) {
				LeaderboardTitle()
				LazyColumn(
					verticalArrangement = Arrangement.spacedBy(16.dp),
				) {
					itemsIndexed(
						items = leaderboardList,
						key = { _, user -> user.username },
					) { index, user ->
						LeaderboardEntry(index, user)
					}
				}
			}
		}
	}
}

@Composable
fun BackButton(isOnLeaderboard: MutableState<Boolean>) {
	Button(
		colors = buttonColors(
			backgroundColor = red200,
			contentColor = Color.White
		),
		elevation = ButtonDefaults.elevation(4.dp),
		modifier = Modifier.absoluteOffset(x = 32.dp, y = 32.dp).size(64.dp),
		shape = MaterialTheme.shapes.medium,
		onClick = { isOnLeaderboard.value = false }
	) {
		Icon(
			imageVector = Icons.Default.West,
			modifier = Modifier.size(32.dp),
			contentDescription = "Back",
		)
	}
}

@Composable
fun LeaderboardTitle() {
	Box(
		contentAlignment = Alignment.Center,
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(6.dp),
		) {
			Row(
				modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
			) {
				LeaderboardText("#", 0.15f)
				LeaderboardText("Username", 0.55f)
				LeaderboardText("Games Played", 0.175f)
				LeaderboardText("Points", 0.125f)
			}

			Divider(color = purple200, thickness = 1.dp)
		}
	}
}

@Composable
fun RowScope.LeaderboardText(
	text: String,
	width: Float,
) {
	Text(
		modifier = Modifier.weight(width),
		softWrap = false,
		style = MaterialTheme.typography.h5,
		text = text,
		textAlign = TextAlign.Start,
	)
}

@Composable
fun LeaderboardEntry(index: Int, user: LeaderboardEntry) {
	Surface(
		color = if (index % 2 == 0) purple400 else purple450,
		modifier = Modifier.fillMaxWidth(),
		shape = MaterialTheme.shapes.medium,
	) {
		Row(
			modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Row(
				horizontalArrangement = Arrangement.spacedBy(16.dp),
				modifier = Modifier.fillMaxWidth(0.15f),
				verticalAlignment = Alignment.CenterVertically,
			) {
				Text(text = user.rank.toString(), style = MaterialTheme.typography.h5)
				if (user.rank in 1..3) Icon(
					contentDescription = "Back",
					imageVector = Icons.Default.EmojiEvents,
					modifier = Modifier.size(42.dp),
					tint = when (user.rank) {
						1 -> Color.Yellow
						2 -> Color.Gray
						3 -> Color(0xFFCD7F32)
						else -> Color.Transparent
					},
				)
			}

			LeaderboardText(user.username, 0.55f)
			LeaderboardText(user.gamePlayed.toString(), 0.175f)
			LeaderboardText(user.points.toString(), 0.125f)
		}
	}
}
