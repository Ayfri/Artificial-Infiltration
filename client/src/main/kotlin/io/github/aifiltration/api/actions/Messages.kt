package io.github.aifiltration.api.actions

import io.github.aifiltration.types.Message
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
private data class CreateMessagePayload(val content: String)

suspend fun createMessage(gameId: Int, text: String) = runCatching {
	post("/games/$gameId/messages/create") {
		contentType(ContentType.Application.Json)
		setBody(CreateMessagePayload(text))
	}
}

suspend fun getMessages(gameId: Int) = runCatching { get("/games/$gameId/messages").body<List<Message>>() }
