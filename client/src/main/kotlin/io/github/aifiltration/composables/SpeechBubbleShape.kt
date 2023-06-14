package io.github.aifiltration.composables

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection

class SpeechBubbleShape(private val arrow: DpSize, private val radius: Dp, private val arrowOnLeftSide: Boolean) :
	Shape {
	override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
		val pxArrow = with(density) { arrow.toSize() }
		val pxRadius = with(density) { radius.toPx() }
		val atLeft = layoutDirection == LayoutDirection.Ltr == arrowOnLeftSide
		val path = Path()
		val left = if (atLeft) pxArrow.width else 0f
		val right = if (atLeft) size.width else size.width - pxArrow.width
		if (atLeft) {
			path.moveTo(left, pxArrow.height)
			path.lineTo(0f, 0f)
			path.arcTo(Rect(right - 2 * pxRadius, 0f, right, 2 * pxRadius), -90f, 90f, false)
		} else {
			path.arcTo(Rect(left, 0f, left + 2 * pxRadius, 2 * pxRadius), -180f, 90f, true)
			path.lineTo(size.width, 0f)
			path.lineTo(right, pxArrow.height)
		}
		path.arcTo(Rect(right - 2 * pxRadius, size.height - 2 * pxRadius, right, size.height), 0f, 90f, false)
		path.arcTo(Rect(left, size.height - 2 * pxRadius, left + 2 * pxRadius, size.height), 90f, 90f, false)
		path.close()
		return Outline.Generic(path)
	}
}
