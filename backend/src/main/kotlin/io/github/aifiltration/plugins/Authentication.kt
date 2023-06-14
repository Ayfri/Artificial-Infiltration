package io.github.aifiltration.plugins

import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.env
import io.github.aifiltration.models.user
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.util.*
import org.komapper.core.dsl.QueryDsl

const val AUTH_NAME = "auth-basic-hashed"
const val REALM = "Login"

private var table = mapOf<String, String>()
val sha512 = getDigestFunction("SHA-512") { "${env("SALT")}${it.length}" }

suspend fun updateHashedTable() = database.withTransaction {
	val result = database.runQuery {
		QueryDsl.from(Tables.user)
	}

	table = result.associate { it.username to it.password }
}

fun Application.configureAuth() {
	authentication {
		basic(AUTH_NAME) {
			realm = REALM
			validate { credentials ->
				updateHashedTable()

				val hashedPassword = sha512(credentials.password)
				val hashedPasswordAsString = hashedPassword.joinToString("") { "%02x".format(it) }

				if (table[credentials.name] == hashedPasswordAsString) {
					UserIdPrincipal(credentials.name)
				} else {
					null
				}
			}
		}

		session<UserSession> {
			challenge {
				call.respond(UnauthorizedResponse())
			}
			validate { it }
		}
	}
}
