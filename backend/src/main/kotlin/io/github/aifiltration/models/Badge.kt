package io.github.aifiltration.models

import org.komapper.annotation.KomapperColumn
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperTable

@KomapperEntity
@KomapperTable("badges")
data class Badge(
	@KomapperColumn("badge_id")
	val id: Int,
	val userId: Int,
)
