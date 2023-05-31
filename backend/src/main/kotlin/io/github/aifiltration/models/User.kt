package io.github.aifiltration.models

import kotlinx.serialization.Serializable
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
	val password: String,
)
