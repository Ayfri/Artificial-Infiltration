package io.github.aifiltration.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val gray300 = Color(0xFF999999)
val gray600 = Color(0xFF404040)
val gray700 = Color(0xFF333333)
val gray800 = Color(0xFF292929)

val green200 = Color(0xFF80EDAC)
val green400 = Color(0xFF3FD457)

val pink300 = Color(0xFFE18DFF)
val pink500 = Color(0xFFB14EFF)

val purple100 = Color(0xFFCFAACB)
val purple200 = Color(0xFFA877A4)
val purple400 = Color(0xFF7D5278)
val purple500 = Color(0xFF5C415A)
val purple600 = Color(0xFF533E51)
val purple800 = Color(0xFF402D3F)

val purpleGray400 = Color(0xFF655B72)

val red200 = Color(0xFFFF7F7F)

val AIColors = lightColors(
	primary = purple400,
	primaryVariant = purple200,
	secondary = purpleGray400,
	secondaryVariant = purple200,
	background = purple500,
	surface = purple500,
	error = red200,
	onPrimary = purple100,
	onSecondary = Color.White,
	onBackground = Color.White,
	onSurface = Color.White,
	onError = Color.White,
)
