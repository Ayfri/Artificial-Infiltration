package io.github.aifiltration.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.komapper.annotation.KomapperAutoIncrement
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable

@KomapperEntity
@KomapperTable("users")
@Serializable
data class User(
	@KomapperAutoIncrement
	@KomapperId
	val id: Int = 0,
	val username: String,
	@Transient
	val password: String = "",
)
