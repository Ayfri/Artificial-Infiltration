package io.github.aifiltration.api.actions

import io.github.aifiltration.LOGGER
import io.github.aifiltration.types.User
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
private data class RegisterPayload(val username: String, val password: String)

suspend fun registerUser(username: String, password: String) = runCatching {
	post("/register") {
		body(RegisterPayload(username, password))
	}.body<User>()
}

suspend fun login() = runCatching {
	LOGGER.info("Cookies : \n${post("/login").setCookie().joinToString("\n") { "${it.name} : ${it.value}" }}")
	post("/login")
}
