package io.github.aifiltration.api.plugins

import io.ktor.client.*
import io.ktor.client.plugins.logging.*

fun HttpClientConfig<*>.configureMonitoring() {
	install(Logging) {
		level = LogLevel.INFO
	}
}
