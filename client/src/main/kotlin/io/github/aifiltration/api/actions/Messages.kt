package io.github.aifiltration.api.actions

import io.github.aifiltration.types.Message
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

@Serializable
private data class CreateMessagePayload(val text: String)

suspend fun createMessage(gameId: Int, text: String) = runCatching {
	post("/games/$gameId/messages") {
		setBody(CreateMessagePayload(text))
	}.body<Message>()
}

suspend fun getMessages(gameId: Int) = runCatching { get("/games/$gameId/messages").body<List<Message>>() }
