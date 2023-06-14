package io.github.aifiltration.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import java.io.File
import kotlin.collections.set
import kotlin.time.Duration.Companion.days

data class UserSession(val userId: Int) : Principal

fun Application.configureSessions() {
	install(Sessions) {
		cookie<UserSession>("session", directorySessionStorage(File("build/.sessions"), cached = true)) {
			cookie.maxAge = 7.days
			cookie.path = "/"
			cookie.extensions["SameSite"] = "lax"
		}
	}
}
