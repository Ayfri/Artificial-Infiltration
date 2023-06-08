package io.github.aifiltration

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import io.github.aifiltration.pages.LoginPage
import io.github.aifiltration.theme.AITheme

fun main() = singleWindowApplication(
	title = "Artificial Infiltration",
	state = WindowState(width = 1600.dp, height = 900.dp, position = WindowPosition.Aligned(Alignment.Center)),
) {
	AITheme {
		LoginPage()
	}
}
