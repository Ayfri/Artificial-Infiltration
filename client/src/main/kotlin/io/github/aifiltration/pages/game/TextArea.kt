package io.github.aifiltration.pages.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.aifiltration.composables.UserAvatar
import io.github.aifiltration.theme.purple800
import io.github.aifiltration.theme.purple900
import io.github.aifiltration.types.User

const val MAX_MESSAGE_LENGTH = 140

@Composable
fun TextArea() {
	val placeholderUser = User(0, "")
	var text by remember { mutableStateOf("") }

	Row(
		modifier = Modifier
			.fillMaxWidth(.75f)
			.background(purple800)
			.padding(horizontal = 16.dp, vertical = 24.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		UserAvatar(placeholderUser)
		TextField(
			value = text,
			onValueChange = {
				if (it.length <= MAX_MESSAGE_LENGTH) text = it.replace(Regex("[\n\t\r]"), "")
			},
			placeholder = {
				Text("Type something sussy...", style = MaterialTheme.typography.body1)
			},
			shape = MaterialTheme.shapes.large,
			colors = TextFieldDefaults.textFieldColors(
				backgroundColor = purple900,
				disabledIndicatorColor = Color.Transparent,
				errorIndicatorColor = Color.Transparent,
				focusedIndicatorColor = Color.Transparent,
				placeholderColor = MaterialTheme.colors.primary,
				textColor = Color.White,
				unfocusedIndicatorColor = Color.Transparent,
			),
			maxLines = 3,
			modifier = Modifier.fillMaxWidth(.92f)
		)

		IconButton(
			onClick = { /*TODO*/ },
			modifier = Modifier
				.background(purple900, shape = CircleShape)
				.size(60.dp)
		) {
			Icon(
				Icons.Filled.Send,
				contentDescription = "Send",
				modifier = Modifier.size(32.dp),
				tint = MaterialTheme.colors.primary
			)
		}
	}
}
