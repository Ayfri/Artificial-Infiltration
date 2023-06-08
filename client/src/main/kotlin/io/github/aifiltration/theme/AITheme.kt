package io.github.aifiltration.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AITheme(
	content: @Composable () -> Unit,
) {
	CompositionLocalProvider {
		MaterialTheme(
			colors = AIColors,
			typography = AITypography,
			shapes = AIShapes,
			content = content
		)
	}
}

object AITheme {
	val colors
		@Composable
		get() = MaterialTheme.colors

	val typography
		@Composable
		get() = MaterialTheme.typography

	val shapes
		@Composable
		get() = MaterialTheme.shapes
}
