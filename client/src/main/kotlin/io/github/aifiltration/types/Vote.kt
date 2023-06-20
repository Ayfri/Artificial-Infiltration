package io.github.aifiltration.types

import kotlinx.serialization.Serializable


@Serializable
data class Vote(
	val authorId: Int,
	val targetUser: User?,
	val valid: Boolean,
)
