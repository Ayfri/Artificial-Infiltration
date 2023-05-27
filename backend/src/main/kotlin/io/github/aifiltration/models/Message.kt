package io.github.aifiltration.models

import io.github.aifiltration.now
import kotlinx.datetime.LocalDateTime
import org.komapper.annotation.KomapperAutoIncrement
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable

@KomapperEntity
@KomapperTable("messages")
data class Message(
	@KomapperAutoIncrement
	@KomapperId
	val id: Int = 0,
	val content: String,
	val authorId: Int,
	val gameId: Int,
	val timestamp: LocalDateTime = LocalDateTime.now(),
)
