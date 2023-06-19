rootProject.name = "Artificial-Infiltration"

include("backend")
include("client")
include("data")

pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
	}

	resolutionStrategy {
		plugins {
			val kotlinVersion = extra["kotlin.version"] as String
			kotlin("jvm") version kotlinVersion
			kotlin("plugin.serialization") version kotlinVersion

			val ktorVersion = extra["ktor.version"] as String
			id("io.ktor.plugin") version ktorVersion
		}
	}
}
