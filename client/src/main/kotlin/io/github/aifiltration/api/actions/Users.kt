package io.github.aifiltration.api.actions

import io.github.aifiltration.types.User
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend fun me() = runCatching {
	val response = get("/me")
	if (!response.status.isSuccess()) throw Exception(response.bodyAsText())
	response.body<User>()
}
