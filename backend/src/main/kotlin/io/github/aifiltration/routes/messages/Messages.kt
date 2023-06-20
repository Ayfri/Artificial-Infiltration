package io.github.aifiltration.routes.messages

import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.getAnonymousColor
import io.github.aifiltration.getAnonymousName
import io.github.aifiltration.models.User
import io.github.aifiltration.models.message
import io.github.aifiltration.models.user
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.operator.asc

@Serializable
private data class MessagePayload(
	val id: Int = 0,
	val content: String,
	val author: User,
	val gameId: Int,
	val timestamp: Instant = Clock.System.now(),
)

fun Route.messages() = get("/games/{gameId}/messages") {
	val gameId = call.parameters["gameId"]?.toIntOrNull()
	if (gameId == null) {
		call.respondText("Invalid game id")
		return@get
	}

	val entityStore = database.runQuery {
		QueryDsl.from(Tables.message).where {
			Tables.message.gameId eq gameId
		}.innerJoin(Tables.user) {
			Tables.message.authorId eq Tables.user.id
		}.orderBy(Tables.message.timestamp.asc()).includeAll()
	}

	val messageAuthors = entityStore.oneToMany(Tables.user, Tables.message)
	val messages = messageAuthors.map { (user, messages) ->
		messages.map { (id, content, _, _, timestamp) ->
			MessagePayload(
				id = id,
				content = content,
				author = User(
					user.id,
					getAnonymousName(user.id),
					getAnonymousColor(user.id).rgb,
				),
				gameId = gameId,
				timestamp = timestamp
			)
		}
	}.flatten()

	call.respond(messages)
}
