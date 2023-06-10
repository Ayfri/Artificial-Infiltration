package io.github.aifiltration.routes.users

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

fun Route.me() {
	get("/me") {
		val callBasicAuthFailures = call.authentication.allFailures
		println(callBasicAuthFailures.joinToString(","))

		val callBasicAuthCredentials = call.attributes
		println(callBasicAuthCredentials)

		val session = call.sessions.get<UserSession>()!!
		val user = database.runQuery {
			QueryDsl.from(Tables.user).where { user.id eq session.userId }.firstOrNull()
		}

		if (user == null) {
			call.sessions.clear<UserSession>()
			call.respond(HttpStatusCode.Unauthorized)
			return@get
		}

		call.respond(HttpStatusCode.OK, user)
	}
}
