package io.github.aifiltration.api.actions

import io.github.aifiltration.api.client
import io.github.aifiltration.storage
import io.ktor.client.request.*

private val url = storage.getOrPut("api_url") { "http://localhost:8080" }

suspend fun get(path: String, block: HttpRequestBuilder.() -> Unit = {}) = client.get(url + path, block)
suspend fun post(path: String, block: HttpRequestBuilder.() -> Unit = {}) = client.post(url + path, block)
suspend fun delete(path: String, block: HttpRequestBuilder.() -> Unit = {}) = client.delete(url + path, block)
