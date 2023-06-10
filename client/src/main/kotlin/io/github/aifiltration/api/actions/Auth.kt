package io.github.aifiltration.api.actions

import io.github.aifiltration.LOGGER
import io.github.aifiltration.types.User
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
private data class RegisterPayload(val username: String, val password: String)

suspend fun registerUser(username: String, password: String): Result<User> {
	val response = post("/register") {
		body(RegisterPayload(username, password))
	}

	return runCatching { response.body() }
}

suspend fun login(): Result<User> {
	val response = post("/login")

	return runCatching {
		LOGGER.info("Cookies : \n${response.setCookie().joinToString("\n") { "${it.name} : ${it.value}" }}")
		response.body()
	}
}
