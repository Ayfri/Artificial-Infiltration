package io.github.aifiltration.routes.users

import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.getAnonymousColor
import io.github.aifiltration.models._User.Companion.user
import io.github.aifiltration.models.user
import io.github.aifiltration.plugins.UserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.firstOrNull

fun Route.me() = get("/me") {
	val session = call.sessions.get<UserSession>() ?: run {
		call.respond(HttpStatusCode.Unauthorized)
		return@get
	}

	val user = database.runQuery {
		QueryDsl.from(Tables.user).where { user.id eq session.userId }.firstOrNull()
	}

	if (user == null) {
		call.respond(HttpStatusCode.Unauthorized)
		call.sessions.clear<UserSession>()
		return@get
	}

	user.color = getAnonymousColor(user.id).rgb

	call.respond(HttpStatusCode.OK, user)
}
