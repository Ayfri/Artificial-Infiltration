package io.github.aifiltration

import io.ktor.util.logging.*
import java.io.File

fun resourceFile(name: String) = File(System.getProperty("compose.application.resources.dir")).also {
	LOGGER.info(
		"System properties: ${
			System.getProperties().toMap().entries.sortedBy { it.key.toString() }.joinToString("\n")
		}"
	)
}.canonicalFile.resolve(name)

val LOGGER = KtorSimpleLogger("io.github.aifiltration.MainLogger")
