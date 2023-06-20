package io.github.aifiltration.ai

import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.getAnonymousName
import io.github.aifiltration.models.message
import io.github.aifiltration.models.user
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.komapper.core.dsl.QueryDsl
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
			ChatCompletionMessage(
				author = getAnonymousName(user.id),
				content = content,
				timestamp = timestamp,
				fromAI = id == AI_ID
			)
		}
	}.flatten().sortedBy { it.timestamp }
}

val defaultMessages = Json.decodeFromString<List<ChatCompletionMessage>>(
	File("src/main/resources/data.json").readText(StandardCharsets.UTF_8)
)
