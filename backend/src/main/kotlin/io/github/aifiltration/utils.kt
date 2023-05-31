package io.github.aifiltration

fun env(name: String, default: String? = null) =
	System.getenv(name) ?: default ?: throw IllegalArgumentException("Environment variable $name is not set.")
