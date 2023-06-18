package io.github.aifiltration.api

import io.github.aifiltration.api.plugins.configureAuthentication
import io.github.aifiltration.api.plugins.configureContentNegotiation
import io.github.aifiltration.api.plugins.configureHttpCookies
import io.github.aifiltration.api.plugins.configureMonitoring
import io.ktor.client.*
import io.ktor.client.engine.cio.*

private var baseClient = HttpClient(CIO) {
	configureAuthentication()
	configureContentNegotiation()
	configureHttpCookies()
	configureMonitoring()
}

val client: HttpClient
	get() {
		baseClient = baseClient.config {
			configureAuthentication()
		}
		return baseClient
	}
