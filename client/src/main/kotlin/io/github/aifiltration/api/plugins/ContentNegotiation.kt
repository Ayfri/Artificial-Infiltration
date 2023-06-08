package io.github.aifiltration.api.plugins

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

fun HttpClientConfig<*>.configureContentNegotiation() {
	install(ContentNegotiation) {
		json()
	}
}
