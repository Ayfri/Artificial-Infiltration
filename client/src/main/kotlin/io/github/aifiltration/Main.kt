package io.github.aifiltration

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import io.github.aifiltration.pages.LoginPage
import io.github.aifiltration.pages.SignUpPage
import io.github.aifiltration.storage.Storage
import io.github.aifiltration.theme.AITheme

val storage = Storage("config.json").apply { load() }

fun main() = singleWindowApplication(
	title = "Artificial Infiltration",
	state = WindowState(width = 1600.dp, height = 900.dp, position = WindowPosition.Aligned(Alignment.Center)),
) {
	AITheme {
		val isLoginPage = remember { mutableStateOf(true) }
		if (isLoginPage.value) LoginPage(isLoginPage)
		else SignUpPage(isLoginPage)
	}
}
