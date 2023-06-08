package io.github.aifiltration.api.actions

import io.ktor.client.request.*
import io.ktor.http.*

inline fun <reified T> HttpRequestBuilder.body(body: T) {
	contentType(ContentType.Application.Json)
	setBody(body)
}
