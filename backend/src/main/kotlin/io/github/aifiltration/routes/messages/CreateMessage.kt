package io.github.aifiltration.routes.messages

import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.models.Message
import io.github.aifiltration.models.game
import io.github.aifiltration.models.message
import io.github.aifiltration.models.userGame
import io.github.aifiltration.plugins.UserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.Serializable
import org.komapper.core.dsl.QueryDsl

@Serializable
private data class CreateMessagePayload(
	val content: String,
)

fun Route.createMessage() = post("/games/{gameId}/messages/create") {
	val id = call.parameters["gameId"]?.toIntOrNull()
	if (id == null) {
		call.respond(HttpStatusCode.BadRequest, "Invalid game id.")
		return@post
	}

	val userSession = call.sessions.get<UserSession>()
	if (userSession == null) {
		call.respond(HttpStatusCode.Unauthorized, "You must be logged in to create a message.")
		return@post
	}

	val entityStore = database.runQuery {
		QueryDsl.from(Tables.game).where {
			Tables.game.id eq id
		}.innerJoin(Tables.userGame) {
			Tables.game.id eq Tables.userGame.gameId
		}.includeAll()
	}

	val gameMembers = entityStore[Tables.userGame].map { it.userId }
	if (userSession.userId !in gameMembers) {
		call.respond(HttpStatusCode.Forbidden, "You are not a member of this game.")
		return@post
	}

	val message = call.receive<CreateMessagePayload>()
	if (message.content.isBlank()) {
		call.respond(HttpStatusCode.BadRequest, "Message content cannot be blank.")
		return@post
	}

	val newMessage = database.runQuery {
		QueryDsl.insert(Tables.message).single(
			Message(
				content = message.content,
				authorId = userSession.userId,
				gameId = id,
			)
		)
	}

	call.respond(HttpStatusCode.Created, newMessage)
}
