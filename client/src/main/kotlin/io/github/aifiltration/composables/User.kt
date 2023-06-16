package io.github.aifiltration.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.aifiltration.types.User

@Composable
fun UserAvatar(user: User, placeholderColor: Color? = null) {
	Box(
		modifier = Modifier
			.size(64.dp)
			.padding(8.dp)
			.background(placeholderColor ?: MaterialTheme.colors.primaryVariant, shape = CircleShape)
	)
}
