package io.github.aifiltration

import io.ktor.util.logging.*
import java.io.File

fun resourceFile(name: String) = File("build/resources/main/$name")

val LOGGER = KtorSimpleLogger("io.github.aifiltration.MainLogger")
