package io.github.aifiltration.pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.aifiltration.composables.AuthBox
import io.github.aifiltration.composables.Page
import io.github.aifiltration.composables.Title

@Composable
@Preview
fun LoginPage() {
	Page {
		AuthBox {
			Title("Login", color = MaterialTheme.colors.onPrimary, modifier = Modifier.padding(top = 16.dp))
		}
	}
}
