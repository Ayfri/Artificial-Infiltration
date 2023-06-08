package io.github.aifiltration.api.actions

import io.github.aifiltration.types.User
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable

@Serializable
private data class RegisterPayload(val username: String, val password: String)

suspend fun registerUser(username: String, password: String): User? {
	val response = post("/register") {
		body(RegisterPayload(username, password))
	}

	return runCatching<User?> {
		response.body()
	}.onFailure {
		println(response.bodyAsText())
	}.getOrNull()
}
