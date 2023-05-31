package io.github.aifiltration.plugins

import io.github.aifiltration.database.Tables
import io.github.aifiltration.database.database
import io.github.aifiltration.env
import io.github.aifiltration.models.user
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.util.*
import org.komapper.core.dsl.QueryDsl

const val AUTH_NAME = "auth-basic-hashed"
const val REALM = "Login"

private val digestFunction = getDigestFunction("SHA-512") { "${env("SALT")}${it.length}" }
private var table = mapOf<String, ByteArray>()

val hashedUserTable get() = UserHashedTableAuth(digestFunction, table)

suspend fun updateHashedTable() = database.withTransaction {
	val result = database.runQuery {
		QueryDsl.from(Tables.user)
	}

	table = result.associate { it.username to digestFunction(it.password) }
}

fun Application.configureAuth() {
	authentication {
		basic(AUTH_NAME) {
			realm = REALM
			validate { credentials ->
				updateHashedTable()
				hashedUserTable.authenticate(credentials)
			}
		}
	}
}
