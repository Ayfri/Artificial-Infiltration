package io.github.aifiltration.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun MessageBox(content: String) {
	val arrow = DpSize(height = 25.dp, width = 15.dp)
	val radius = 10.dp

	Box(
		modifier = Modifier
			.padding(top = 28.dp, start = 8.dp, end = 8.dp)
			.fillMaxWidth()
			.clip(SpeechBubbleShape(arrow, radius, true))
			.background(MaterialTheme.colors.primary)
			.padding(start = arrow.width + radius, end = radius),
	) {
		Column(
			modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
		) {
			Text(text = content, style = MaterialTheme.typography.body2)
		}
	}
}
