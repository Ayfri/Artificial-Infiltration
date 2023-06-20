package io.github.aifiltration.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.komapper.annotation.*

@KomapperEntity
@KomapperTable("users")
@Serializable
data class User(
	@KomapperAutoIncrement
	@KomapperId
	val id: Int = 0,
	val username: String,
	@KomapperIgnore
	var color: Int = 0,
	@Transient
	val password: String = "",
)
