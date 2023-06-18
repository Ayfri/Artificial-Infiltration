package io.github.aifiltration.composables

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager


@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.moveFocusOnTab() = composed {
	val focusManager = LocalFocusManager.current
	onPreviewKeyEvent {
		if (it.type == KeyEventType.KeyDown && it.key == Key.Tab) {
			focusManager.moveFocus(
				if (it.isShiftPressed) FocusDirection.Previous else FocusDirection.Next
			)
			true
		} else {
			false
		}
	}
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.onEnterKeyPressed(onEnterPressed: () -> Unit) = composed {
	onPreviewKeyEvent {
		if (it.type == KeyEventType.KeyDown && (it.key == Key.Enter || it.key == Key.NumPadEnter)) {
			onEnterPressed()
			true
		} else {
			false
		}
	}
}
