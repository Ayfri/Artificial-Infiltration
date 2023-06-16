package io.github.aifiltration.api.actions

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable


@Serializable
data class CreateVotePayload(val targetId: Int)

suspend fun vote(gameId: Int, targetId: Int) = runCatching {
	post("/games/$gameId/vote") {
		contentType(ContentType.Application.Json)
		setBody(CreateVotePayload(targetId))
	}
}
