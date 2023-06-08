package io.github.aifiltration.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable

@Composable
fun Page(
	content: @Composable (PaddingValues) -> Unit = {}
) {
	Scaffold(
		backgroundColor = MaterialTheme.colors.background,
		content = content
	)
}
