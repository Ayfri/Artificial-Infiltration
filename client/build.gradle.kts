val composeVersion = project.ext["compose.version"] as String

plugins {
	kotlin("jvm")
	kotlin("plugin.serialization")
	id("io.ktor.plugin")
	id("org.jetbrains.compose") version "1.4.0"
}

repositories {
	mavenCentral()
	maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
	google()
}

dependencies {
	implementation(compose.desktop.currentOs)
}
