package io.github.aifiltration.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import io.github.aifiltration.theme.AITheme

@Composable
fun Title(
	text: String,
	color: Color = MaterialTheme.colors.onSurface,
	modifier: Modifier = Modifier,
) {
	Text(
		color = color,
		modifier = modifier,
		style = AITheme.typography.h1,
		text = text,
		textAlign = TextAlign.Center
	)
}
