import org.jetbrains.compose.desktop.application.dsl.TargetFormat

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

		nativeDistributions {
			packageVersion = "0.1.0"
			version = "0.1.0"
			vendor = "Slicer"
			description =
				"Artificial Infiltration est une application où des joueurs doivent identifier en 3 minutes parmi un maximum de 5 participants celui qui est en réalité une intelligence artificielle afin de gagner des points selon l'ordre de leur vote correct et suivre leur classement total."

			includeAllModules = true
			targetFormats(TargetFormat.Exe)
		}
	}
}
