package io.github.aifiltration.pages.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.aifiltration.cacheAppData
import io.github.aifiltration.composables.UserAvatar
import io.github.aifiltration.pages.GAME_WIDTH_RATIO
import io.github.aifiltration.theme.green400
import io.github.aifiltration.theme.red200
import io.github.aifiltration.types.User
import io.github.aifiltration.types.Vote

@Composable
fun EndGame(realAI: User) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = Modifier.fillMaxWidth(GAME_WIDTH_RATIO).fillMaxHeight(),
		verticalArrangement = Arrangement.spacedBy(24.dp),
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Text("The real AI was:", style = MaterialTheme.typography.h1)
			UserPresentation(realAI, 2.0)
		}

		Row(
			horizontalArrangement = Arrangement.SpaceEvenly,
			modifier = Modifier.fillMaxWidth(.8f),
			verticalAlignment = Alignment.Top,
		) {
			cacheAppData.votes.value.filter {
				it.authorId != realAI.id
			}.forEach { vote ->
				val user = cacheAppData.members.value.first { it.id == vote.authorId }
				UserVote(user, vote)
			}
		}
	}
}

@Composable
fun UserPresentation(user: User, scale: Double = 1.0) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		UserAvatar(user, (96 * scale).dp)
		Text(user.username, fontSize = 24.sp * scale, style = MaterialTheme.typography.h1)
	}
}

@Composable
fun UserVote(user: User, vote: Vote) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(12.dp),
	) {
		UserPresentation(user)
		Box(
			modifier = Modifier
				.background(if (vote.valid) green400 else red200, MaterialTheme.shapes.medium)
				.width(196.dp),
			contentAlignment = Alignment.Center,
		) {
			Text(
				text = when {
					vote.valid -> "Found"
					vote.targetUser == null -> "Guessed\nNo one"
					else -> "Guessed\n${vote.targetUser.username}"
				},
				fontSize = 24.sp,
				modifier = Modifier.padding(6.dp),
				style = MaterialTheme.typography.h1,
				textAlign = TextAlign.Center,
			)
		}
	}
}
