package io.github.aifiltration.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.aifiltration.types.User

@Composable
fun UserAvatar(user: User, scale: Dp = 64.dp) {
	Box(
		modifier = Modifier
			.size(scale)
			.padding(8.dp)
			.background(randomColorFromUser(user), shape = CircleShape)
	)
}

private val usedColors = mutableMapOf<User, Color>()
private const val RANDOM_COLORS_COUNT = 20
private val colors = (0..RANDOM_COLORS_COUNT).map {
	Color.hsv(
		360f / RANDOM_COLORS_COUNT * it,
		0.4f,
		0.9f
	)
}

private fun randomColorFromUser(user: User): Color {
	if (user in usedColors) return usedColors[user]!!

	val availableColors = colors.filter { it !in usedColors.values }
	val index = (user.id - 1) % colors.size
	val color = availableColors.getOrElse(index) {
		usedColors.clear()
		availableColors.first()
	}
	usedColors[user] = color
	return color
}
