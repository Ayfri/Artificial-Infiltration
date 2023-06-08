val composeVersion = project.ext["compose.version"] as String

plugins {
	kotlin("jvm")
	kotlin("plugin.serialization")
	id("org.jetbrains.compose") version "1.4.0"
//	id("io.ktor.plugin")
//	application
}

repositories {
	mavenCentral()
	maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
	google()
}

dependencies {
	implementation(compose.desktop.currentOs)
	implementation(compose.preview)
}

compose.desktop {
	application {
		mainClass = "io.github.aifiltration.MainKt"
	}
}

/*
application {
	mainClass.set("io.github.aifiltration.MainKt")
}
*/
