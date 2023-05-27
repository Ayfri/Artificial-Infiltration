package io.github.aifiltration.models

import org.komapper.annotation.KomapperAutoIncrement
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable

@KomapperEntity
@KomapperTable("votes")
data class Vote(
	@KomapperAutoIncrement
	@KomapperId
	val id: Int = 0,
	val authorId: Int,
	val gameId: Int,
	val targetId: Int,
)
