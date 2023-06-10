package io.github.aifiltration.routes.auth

import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.models._User.Companion.user
import io.github.aifiltration.models.user
import io.github.aifiltration.plugins.UserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.firstOrNull

fun Route.login() {
	post("/login") {
		val oldSession = call.sessions.get<UserSession>()
		if (oldSession != null) {
			call.respond(HttpStatusCode.OK)
			return@post
		}

		val username = call.principal<UserIdPrincipal>()!!.name
		val userId = database.runQuery {
			QueryDsl.from(Tables.user).where { user.username eq username }.select(Tables.user.id).firstOrNull()
		}

		if (userId == null) {
			call.respondRedirect("/register")
			return@post
		}

		call.sessions.set(UserSession(userId))
		call.respond(HttpStatusCode.OK)
	}
}
