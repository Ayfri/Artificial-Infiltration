package io.github.aifiltration.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import io.github.aifiltration.theme.purple100
import io.github.aifiltration.types.Message
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MessageBox(message: Message) {
	val arrow = DpSize(height = 25.dp, width = 15.dp)
	val radius = 10.dp

	Box(
		modifier = Modifier
			.padding(top = 28.dp, start = 8.dp, end = 8.dp)
			.fillMaxWidth()
			.clip(SpeechBubbleShape(arrow, radius, true))
			.background(MaterialTheme.colors.primary)
			.padding(start = arrow.width + radius, end = radius),
	) {
		Box(
			modifier = Modifier.padding(horizontal = 8.dp).padding(top = 16.dp, bottom = 6.dp).fillMaxSize(),
		) {
			Text(
				message.content,
				modifier = Modifier.padding(bottom = 10.dp),
				style = MaterialTheme.typography.body2,
			)

			val dateTime = message.timestamp.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
			val time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
			Text(
				time,
				color = purple100,
				modifier = Modifier.align(alignment = Alignment.BottomEnd),
				style = MaterialTheme.typography.caption
			)
		}
	}
}
