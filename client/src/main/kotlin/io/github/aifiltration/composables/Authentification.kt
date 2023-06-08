package io.github.aifiltration.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.github.aifiltration.theme.AIElevations
import io.github.aifiltration.theme.purple900


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
			elevation = AIElevations.card,
			modifier = Modifier.fillMaxHeight(.8f).fillMaxWidth(.33f).align(Alignment.Center),
		) {
			Column(
				verticalArrangement = Arrangement.SpaceBetween,
				modifier = Modifier.fillMaxSize().padding(16.dp)
			) {
				content()
			}
		}
	}
}

@Composable
fun AuthInput(
	value: String,
	placeholder: String = "",
	keyboardType: KeyboardType = KeyboardType.Text,
	icon: @Composable () -> Unit = {},
	onValueChange: (String) -> Unit = {},
) {
	TextField(
		colors = TextFieldDefaults.textFieldColors(
			backgroundColor = purple900,
			leadingIconColor = MaterialTheme.colors.onPrimary,
			cursorColor = MaterialTheme.colors.onPrimary,
			textColor = MaterialTheme.colors.onPrimary,
			focusedIndicatorColor = Color.Transparent,
			unfocusedIndicatorColor = Color.Transparent,
			disabledIndicatorColor = Color.Transparent,
		),
		keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
		leadingIcon = icon,
		modifier = Modifier.fillMaxWidth().padding(16.dp),
		onValueChange = onValueChange,
		placeholder = { Text(placeholder) },
		shape = MaterialTheme.shapes.medium,
		singleLine = true,
		textStyle = MaterialTheme.typography.body1,
		value = value,
		visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
	)
}

@Composable
fun AuthButton(
	text: String,
	color1: Color,
	color2: Color,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	onClick: () -> Unit = {},
) {
	Button(
		colors = ButtonDefaults.buttonColors(
			backgroundColor = Color.Transparent,
			contentColor = MaterialTheme.colors.onPrimary,
		),
		contentPadding = PaddingValues(),
		interactionSource = interactionSource,
		modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
		onClick = onClick,
		shape = MaterialTheme.shapes.medium
	) {
		Box(
			contentAlignment = Alignment.Center,
			modifier = Modifier.background(
				brush = Brush.horizontalGradient(
					colors = listOf(color1, color2)
				)
			).padding(16.dp).width(200.dp)
		) {
			Text(
				text = text,
				color = Color.White,
			)
		}
	}
}
