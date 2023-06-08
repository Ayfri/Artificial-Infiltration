package io.github.aifiltration.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun AuthBox(
	content: @Composable () -> Unit = {},
) {
	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier.fillMaxSize()
	) {
		Card(
			backgroundColor = MaterialTheme.colors.primary,
			modifier = Modifier.fillMaxHeight(.8f).fillMaxWidth(.33f).align(Alignment.Center),
			content = content
		)
	}
}
