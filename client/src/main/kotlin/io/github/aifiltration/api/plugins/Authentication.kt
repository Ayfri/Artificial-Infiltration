package io.github.aifiltration.api.plugins

import io.ktor.client.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*

fun HttpClientConfig<*>.configureAuthentication() {
	install(Auth) {
		basic {
			credentials {
				BasicAuthCredentials(username = "test", password = "test")
			}
			realm = "Login"
		}
	}
}
