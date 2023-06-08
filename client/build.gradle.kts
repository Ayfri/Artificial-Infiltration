val dotenvVersion = project.ext["dotenv.version"] as String
val composeVersion = project.ext["compose.version"] as String
val ktorVersion = project.ext["ktor.version"] as String
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

	implementation("io.ktor:ktor-client-auth:$ktorVersion")
	implementation("io.ktor:ktor-client-cio:$ktorVersion")
	implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
	implementation("io.ktor:ktor-client-core:$ktorVersion")
	implementation("io.ktor:ktor-client-logging:$ktorVersion")
	implementation("ch.qos.logback:logback-classic:$logbackVersion")
	implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

	implementation("io.github.cdimascio:dotenv-kotlin:$dotenvVersion")
}

compose.desktop {
	application {
		mainClass = "io.github.aifiltration.MainKt"
	}
}
