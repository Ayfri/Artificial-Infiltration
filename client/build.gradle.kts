val composeVersion = project.ext["compose.version"] as String
val ktorVersion = project.ext["ktor.version"] as String
val kotlinxDateTimeVersion = project.ext["kotlinx.datetime.version"] as String
val logbackVersion = project.ext["logback.version"] as String

plugins {
	kotlin("jvm")
	kotlin("plugin.serialization")
	id("org.jetbrains.compose") version "1.4.0"
}

repositories {
	mavenCentral()
	maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
	google()
}

dependencies {
	implementation(compose.desktop.currentOs)
	implementation(compose.materialIconsExtended)
	implementation(compose.preview)

	implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinxDateTimeVersion")

	implementation("io.ktor:ktor-client-auth:$ktorVersion")
	implementation("io.ktor:ktor-client-cio:$ktorVersion")
	implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
	implementation("io.ktor:ktor-client-core:$ktorVersion")
	implementation("io.ktor:ktor-client-logging:$ktorVersion")
	implementation("ch.qos.logback:logback-classic:$logbackVersion")
	implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
}

compose.desktop {
	application {
		mainClass = "io.github.aifiltration.MainKt"
	}
}
