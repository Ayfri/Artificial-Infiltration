package io.github.aifiltration

import io.github.cdimascio.dotenv.dotenv
import io.ktor.util.logging.*

val dotEnv = dotenv()

val LOGGER = KtorSimpleLogger("io.github.aifiltration.MainLogger")

fun env(name: String, default: String? = null) =
	dotEnv[name, default] ?: throw IllegalArgumentException("Environment variable $name is not set.")
