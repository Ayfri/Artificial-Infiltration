package io.github.aifiltration.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp
import io.github.aifiltration.resourceFile


private val font = FontFamily(
	Font(resourceFile("fonts/MPLUSRounded1c-Black.ttf"), FontWeight.Black),
	Font(resourceFile("fonts/MPLUSRounded1c-Bold.ttf"), FontWeight.Bold),
	Font(resourceFile("fonts/MPLUSRounded1c-ExtraBold.ttf"), FontWeight.ExtraBold),
	Font(resourceFile("fonts/MPLUSRounded1c-Light.ttf"), FontWeight.Light),
	Font(resourceFile("fonts/MPLUSRounded1c-Medium.ttf"), FontWeight.Medium),
	Font(resourceFile("fonts/MPLUSRounded1c-Regular.ttf"), FontWeight.Normal),
	Font(resourceFile("fonts/MPLUSRounded1c-Thin.ttf"), FontWeight.Thin),
)

val AITypography = Typography(
	defaultFontFamily = font,
	h1 = TextStyle(
		fontFamily = font,
		fontWeight = FontWeight.Bold,
		fontSize = 64.sp,
		letterSpacing = (-1.5).sp
	),
	h2 = TextStyle(
		fontFamily = font,
		fontWeight = FontWeight.Bold,
		fontSize = 48.sp,
		letterSpacing = (-0.5).sp
	),
	h3 = TextStyle(
		fontFamily = font,
		fontWeight = FontWeight.Medium,
		fontSize = 40.sp,
		letterSpacing = 0.sp
	),
	h4 = TextStyle(
		fontFamily = font,
		fontWeight = FontWeight.Medium,
		fontSize = 34.sp,
		letterSpacing = 0.25.sp
	),
	h5 = TextStyle(
		fontFamily = font,
		fontWeight = FontWeight.Medium,
		fontSize = 24.sp,
		letterSpacing = 0.sp
	),
	h6 = TextStyle(
		fontFamily = font,
		fontWeight = FontWeight.Normal,
		fontSize = 20.sp,
		letterSpacing = 0.15.sp
	),
	subtitle1 = TextStyle(
		fontFamily = font,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp,
		letterSpacing = 0.15.sp
	),
	subtitle2 = TextStyle(
		fontFamily = font,
		fontWeight = FontWeight.Normal,
		fontSize = 14.sp,
		letterSpacing = 0.1.sp
	),
	body1 = TextStyle(
		fontFamily = font,
		fontWeight = FontWeight.Bold,
		fontSize = 20.sp,
		letterSpacing = 0.5.sp
	),
	body2 = TextStyle(
		fontFamily = font,
		fontWeight = FontWeight.Bold,
		fontSize = 16.sp,
		letterSpacing = 0.25.sp
	),
	button = TextStyle(
		fontFamily = font,
		fontWeight = FontWeight.Bold,
		fontSize = 24.sp,
		letterSpacing = 1.25.sp
	)
)
