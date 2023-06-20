package io.github.aifiltration.models

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import org.komapper.annotation.KomapperAutoIncrement
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable

@KomapperEntity
@KomapperTable("votes")
@Serializable
data class Vote(
	@KomapperAutoIncrement
	@KomapperId
	val id: Int = 0,
	val authorId: Int,
	val createdAt: Instant = Clock.System.now(),
	val gameId: Int,
	val targetId: Int,
)
