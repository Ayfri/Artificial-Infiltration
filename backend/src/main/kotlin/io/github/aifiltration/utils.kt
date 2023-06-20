package io.github.aifiltration

import io.github.cdimascio.dotenv.dotenv
import io.ktor.util.logging.*
import java.awt.Color

val dotEnv = dotenv()

val LOGGER = KtorSimpleLogger("io.github.aifiltration.MainLogger")

fun env(name: String, default: String? = null) =
	dotEnv[name, default] ?: throw IllegalArgumentException("Environment variable $name is not set.")

fun randomColor() = Color((Math.random() * 0x1000000).toInt())
