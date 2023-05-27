package io.github.aifiltration

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun env(name: String, default: String? = null) =
	System.getenv(name) ?: default ?: throw IllegalArgumentException("Environment variable $name is not set.")

fun LocalDateTime.Companion.now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
