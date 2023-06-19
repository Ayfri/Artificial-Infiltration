plugins {
	kotlin("jvm")
	kotlin("plugin.serialization")
}

dependencies {
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.5.1")
}

repositories {
	mavenCentral()
}
