package io.github.aifiltration.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import io.github.aifiltration.theme.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardEntry(
	val gamePlayed: Int,
	val points: Int,
	val username: String,
)

lateinit var lazyColumnState: LazyListState
var leaderboardList by mutableStateOf(listOf<LeaderboardEntry>())
var sortedByPoints by mutableStateOf(true)

@Composable
fun LeaderboardPage(isOnLeaderboard: MutableState<Boolean>) {
	lazyColumnState = rememberLazyListState()
	LaunchedEffect(leaderboardList) {
		leaderboardList = leaderboard().getOrDefault(listOf())
	}

	Page {
		BackButton(isOnLeaderboard)

		Box(
			contentAlignment = Alignment.TopCenter,
			modifier = Modifier.fillMaxSize(),
		) {
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(16.dp),
			) {
				Row(
					horizontalArrangement = Arrangement.SpaceEvenly,
					modifier = Modifier.fillMaxWidth(.6f).padding(top = 64.dp, bottom = 32.dp),
				) {
					SortingLeaderboardButton("Points") {
						sortedByPoints = true
					}

					SortingLeaderboardButton("Games Played") {
						sortedByPoints = false
					}
				}

				Column(
					horizontalAlignment = Alignment.CenterHorizontally,
					modifier = Modifier.fillMaxWidth(.85f),
					verticalArrangement = Arrangement.spacedBy(12.dp),
				) {
					LeaderboardTitle()
					LazyColumn(
						modifier = Modifier.padding(bottom = 48.dp),
						state = lazyColumnState,
						verticalArrangement = Arrangement.spacedBy(16.dp),
					) {
						itemsIndexed(
							items = leaderboardList.sortedByDescending {
								if (sortedByPoints) it.points else it.gamePlayed
							},
							key = { _, user -> user.username },
						) { index, user ->
							LeaderboardEntry(index + 1, user)
						}
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
fun SortingLeaderboardButton(
	text: String,
	enabled: Boolean = true,
	onClick: () -> Unit,
) {
	val coroutineScope = rememberCoroutineScope()
	Button(
		colors = buttonColors(
			backgroundColor = pink300,
			contentColor = Color.White
		),
		contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
		elevation = ButtonDefaults.elevation(4.dp),
		enabled = enabled,
		onClick = {
			onClick()
			coroutineScope.launch {
				lazyColumnState.animateScrollToItem(0)
			}
		},
		shape = MaterialTheme.shapes.medium,
	) {
		Text(text = text, style = MaterialTheme.typography.h4)
	}
}

@Composable
fun LeaderboardEntry(rank: Int, user: LeaderboardEntry) {
	Surface(
		color = if (rank % 2 == 0) purple400 else purple450,
		contentColor = Color.White,
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
				Text(text = rank.toString(), style = MaterialTheme.typography.h5)
				if (rank in 1..3) Icon(
					contentDescription = "Back",
					imageVector = Icons.Default.EmojiEvents,
					modifier = Modifier.size(42.dp),
					tint = when (rank) {
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
