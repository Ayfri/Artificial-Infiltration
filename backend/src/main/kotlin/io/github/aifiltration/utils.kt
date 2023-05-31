package io.github.aifiltration

import io.github.cdimascio.dotenv.dotenv

val dotEnv = dotenv()

fun env(name: String, default: String? = null) =
	dotEnv[name, default] ?: throw IllegalArgumentException("Environment variable $name is not set.")
