package io.github.aifiltration.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Page(
	modifier: Modifier = Modifier,
	content: @Composable (PaddingValues) -> Unit = {},
) {
	Scaffold(
		backgroundColor = MaterialTheme.colors.background,
		content = content,
		modifier = modifier
	)
}
