package io.github.aifiltration.api.actions

import io.github.aifiltration.types.User
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.date.*
import kotlinx.serialization.Serializable

@Serializable
private data class RegisterPayload(val username: String, val password: String)

suspend fun registerUser(username: String, password: String) = runCatching {
	post("/register") {
		body(RegisterPayload(username, password))
	}.body<User>()
}

suspend fun login() = runCatching {
	post("/login") {
		cookie("session", "", maxAge = 0, expires = GMTDate())
	}
}

suspend fun logout() = runCatching { post("/logout") }
