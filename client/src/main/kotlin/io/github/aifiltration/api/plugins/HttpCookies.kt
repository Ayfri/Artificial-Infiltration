package io.github.aifiltration.api.plugins

import io.github.aifiltration.storage.Storage
import io.ktor.client.*
import io.ktor.client.plugins.cookies.*
import io.ktor.http.*
import io.ktor.util.date.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString

fun HttpClientConfig<*>.configureHttpCookies() {
	install(HttpCookies) {
		storage = FileCookiesStorage()
	}
}

@Serializable
private data class CookieSerialized(
	val name: String,
	val value: String,
	val expires: Instant,
) {
	constructor(cookie: Cookie) : this(
		cookie.name,
		cookie.value,
		Instant.fromEpochMilliseconds(cookie.expires?.timestamp ?: 0)
	)
}

private class FileCookiesStorage : CookiesStorage {
	private val storage = Storage("cookies.json", verbose = true)

	override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
		val cookies = getCookies(requestUrl)
		cookies += CookieSerialized(cookie)
		storage[getKey(requestUrl)] = storage.json.encodeToString(cookies)
		storage.save()
	}

	override fun close() {
		storage.save()
	}

	override suspend fun get(requestUrl: Url): List<Cookie> {
		storage.load()
		val cookies = getCookies(requestUrl)
		return cookies.map {
			Cookie(
				name = it.name,
				value = it.value,
				expires = GMTDate(it.expires.toEpochMilliseconds()),
			)
		}
	}

	private fun getKey(requestUrl: Url) = requestUrl.host

	private fun getCookies(requestUrl: Url) =
		storage.json.decodeFromString<MutableList<CookieSerialized>>(storage[getKey(requestUrl)] ?: "[]").apply {
			removeIf { it.expires.toGMTDate() < GMTDate() }
		}
}

private fun Instant.toGMTDate() = GMTDate(toEpochMilliseconds())
