package io.github.aifiltration

import io.ktor.util.logging.*
import java.io.File

fun resourceFile(name: String) = File("./src/main/resources/common/$name")

val LOGGER = KtorSimpleLogger("io.github.aifiltration.MainLogger")
