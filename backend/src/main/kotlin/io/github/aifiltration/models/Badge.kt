package io.github.aifiltration.models

import kotlinx.serialization.Serializable
import org.komapper.annotation.KomapperColumn
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable

@KomapperEntity
@KomapperTable("badges")
@Serializable
data class Badge(
	@KomapperColumn("badge_id")
	@KomapperId(virtual = true)
	val id: Int,
	@KomapperId(virtual = true)
	val userId: Int,
)
