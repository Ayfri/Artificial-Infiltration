package io.github.aifiltration.api.actions

import io.github.aifiltration.api.client
import io.github.aifiltration.env
import io.ktor.client.request.*

private val url = env("API_URL")

suspend fun get(path: String, block: HttpRequestBuilder.() -> Unit = {}) = client.get(url + path, block)
suspend fun post(path: String, block: HttpRequestBuilder.() -> Unit = {}) = client.post(url + path, block)
