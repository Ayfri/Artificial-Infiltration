package io.github.aifiltration.api.plugins

import io.github.aifiltration.storage
import io.ktor.client.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*

fun HttpClientConfig<*>.configureAuthentication() {
	install(Auth) {
		basic {
			credentials {
				val username = storage["username"] ?: ""
				val password = storage["password"] ?: ""
				BasicAuthCredentials(username, password)
			}
			realm = "Login"
		}
	}
}
