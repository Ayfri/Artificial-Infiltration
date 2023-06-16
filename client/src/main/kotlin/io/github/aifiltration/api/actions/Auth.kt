package io.github.aifiltration.api.actions

import io.github.aifiltration.types.User
import io.ktor.client.call.*
import kotlinx.serialization.Serializable

@Serializable
private data class RegisterPayload(val username: String, val password: String)

suspend fun registerUser(username: String, password: String) = runCatching {
	post("/register") {
		body(RegisterPayload(username, password))
	}.body<User>()
}

suspend fun login() = runCatching { post("/login") }
