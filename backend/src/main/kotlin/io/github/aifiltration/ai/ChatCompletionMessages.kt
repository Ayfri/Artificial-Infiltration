package io.github.aifiltration.ai

import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.models.Message
import io.github.aifiltration.models.message
import io.github.aifiltration.models.user
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.map
import java.io.File
import java.nio.charset.StandardCharsets

@Serializable
data class ChatCompletionMessage(
	val author: String,
	val content: String,
	val timestamp: Instant = Instant.DISTANT_PAST,
	val fromAI: Boolean = false,
)

const val AI_ID = 10000000

suspend fun queryChatCompletionMessages(
	gameId: Int,
): List<ChatCompletionMessage> {
	val entityStore = database.runQuery {
		QueryDsl.from(Tables.message).where {
			Tables.message.gameId eq gameId
		}.innerJoin(Tables.user) {
			Tables.message.authorId eq Tables.user.id
		}.includeAll()
	}

	val messageAuthors = entityStore.oneToMany(Tables.user, Tables.message)
	return messageAuthors.map { (user, messages) ->
		messages.map { (id, content, _, _, timestamp) ->
			ChatCompletionMessage(user.username, content, timestamp, id == AI_ID)
		}
	}.flatten().sortedBy { it.timestamp }
}

suspend fun queryChatCompletionMessages(
	messages: List<Message> = emptyList(),
) = database.runQuery {
	QueryDsl.from(Tables.user).where {
		Tables.user.id inList messages.map { it.authorId }
	}.map { users ->
		users.map { user -> user to messages.filter { it.authorId == user.id } }
	}
}.map { (user, messages) ->
	messages.map { ChatCompletionMessage(user.username, it.content, it.timestamp, it.id == AI_ID) }
}.flatten()

val defaultMessages = Json.decodeFromString<List<ChatCompletionMessage>>(
	File("src/main/resources/data.json").readText(StandardCharsets.UTF_8)
)
