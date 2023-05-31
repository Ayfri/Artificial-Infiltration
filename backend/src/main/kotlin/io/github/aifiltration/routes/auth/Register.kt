package io.github.aifiltration.routes.auth

import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.models.User
import io.github.aifiltration.models._User.Companion.user
import io.github.aifiltration.models.user
import io.github.aifiltration.plugins.UserSession
import io.github.aifiltration.plugins.updateHashedTable
import io.github.aifiltration.routes.USER_ALREADY_EXISTS
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.Serializable
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.firstOrNull

@Serializable
data class RegisterRequest(val username: String, val password: String)

fun Route.register() = post("/register") {
	val request = call.receive<RegisterRequest>()
	database.withTransaction {
		val alreadyExists = database.runQuery {
			QueryDsl.from(Tables.user).where { user.username eq request.username }.firstOrNull()
		}

		if (alreadyExists != null) {
			call.respond(HttpStatusCode.Conflict, USER_ALREADY_EXISTS)
			return@withTransaction
		}

		val result = database.runQuery {
			QueryDsl.insert(Tables.user).single(User(username = request.username, password = request.password))
		}

		call.sessions.set(UserSession(result.id))
		updateHashedTable()

		call.respond(HttpStatusCode.Created, result)
	}
}
