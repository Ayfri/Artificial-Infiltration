package io.github.aifiltration.storage

import io.github.aifiltration.LOGGER
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException

data class Storage(
	val filename: String,
	val pretty: Boolean = false,
	val verbose: Boolean = false,
	val content: MutableMap<String, String> = mutableMapOf(),
) : MutableMap<String, String> by content {
	val json = Json {
		prettyPrint = pretty
		ignoreUnknownKeys = true
	}

	fun load(): Boolean {
		val result = runCatching {
			val file = File(filename)
			val content = file.readText()
			val values = json.decodeFromString<Map<String, String>>(content)
			this.content.clear()
			this.content.putAll(values)
		}

		result.onFailure {
			if (it !is FileNotFoundException) LOGGER.error("Failed to load $filename.", it)
		}

		if (verbose) result.onSuccess { LOGGER.debug("Loaded $filename.") }
		return result.isSuccess
	}

	fun save(): Boolean {
		val result = runCatching {
			val file = File(filename).canonicalFile
			file.parentFile?.mkdirs()
			if (!file.exists()) file.createNewFile()

			val content = json.encodeToString(content)
			file.writeText(content)
		}

		result.onFailure { LOGGER.error("Failed to save $filename.", it) }
		if (verbose) result.onSuccess { LOGGER.debug("Saved $filename.") }
		return result.isSuccess
	}
}
