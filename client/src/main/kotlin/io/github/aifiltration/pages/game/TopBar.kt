package io.github.aifiltration.pages.game

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import io.github.aifiltration.api.actions.vote
import io.github.aifiltration.cacheAppData
import io.github.aifiltration.composables.UserAvatar
import io.github.aifiltration.resourceFile
import io.github.aifiltration.storage
import io.github.aifiltration.theme.green400
import io.github.aifiltration.theme.purple650
import io.github.aifiltration.types.User
import io.ktor.http.*
import kotlinx.coroutines.runBlocking


@Composable
fun TopBar(users: List<User>) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.background(purple650)
			.padding(horizontal = 24.dp, vertical = 4.dp)
			.zIndex(1f),
		horizontalArrangement = Arrangement.spacedBy(96.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Image(
			bitmap = loadImageBitmap(resourceFile("images/skull.png").inputStream()),
			contentDescription = "Logo",
			contentScale = ContentScale.FillWidth,
			modifier = Modifier.size(96.dp)
		)
		MemberList(users)
	}
}

@Composable
fun MemberList(users: List<User>) {
	Row(
		horizontalArrangement = Arrangement.spacedBy(75.dp),
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier.wrapContentSize()
	) {
		users.forEach {
			MemberComponent(it)
		}
	}
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MemberComponent(user: User) {
	var hover by rememberSaveable { mutableStateOf(false) }
	val animatedSizeDp by animateDpAsState(
		targetValue = if (
			hover &&
			!cacheAppData.hasVoted.value &&
			!cacheAppData.currentGameFinished.value
		) 0.dp else 220.dp
	)
	var isVoted by mutableStateOf(storage["voteTarget"] == user.id.toString())

	Surface(
		modifier = Modifier
			.onPointerEvent(PointerEventType.Enter) { hover = true }
			.onPointerEvent(PointerEventType.Exit) { hover = false },
		shape = CircleShape,
		color = if (isVoted) green400 else MaterialTheme.colors.primary,
	) {
		Row(
			modifier = Modifier
				.padding(end = 16.dp)
				.width(200.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			UserAvatar(user)
			Box(
				modifier = Modifier.fillMaxWidth()
			) {
				Text(
					color = Color.White,
					modifier = Modifier.fillMaxWidth().align(Alignment.Center),
					fontSize = 14.sp,
					text = user.username,
					textAlign = TextAlign.Center
				)

				Button(
					colors = buttonColors(
						backgroundColor = green400,
						contentColor = MaterialTheme.colors.onSurface
					),
					contentPadding = PaddingValues(6.dp),
					modifier = Modifier
						.padding(start = animatedSizeDp)
						.requiredWidth(100.dp)
						.align(Alignment.Center),
					onClick = {
						runBlocking {
							val gameId = cacheAppData.currentGame!!.id
							val response = vote(gameId, user.id).getOrThrow()
							if (response.status.isSuccess()) {
								storage["voteTarget"] = user.id.toString()
								cacheAppData.hasVoted.value = true
								isVoted = true
							}
						}
					}
				) {
					Text("Vote", fontSize = 16.sp)
				}
			}
		}
	}
}
