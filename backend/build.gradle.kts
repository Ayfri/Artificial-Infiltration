val dotenvVersion = project.ext["dotenv.version"] as String
val logbackVersion = project.ext["logback.version"] as String
val komapperVersion = project.ext["komapper.version"] as String
val kotlinxDateTimeVersion = project.ext["kotlinx.datetime.version"] as String
val ktorVersion = project.ext["ktor.version"] as String
val openAIVersion = project.ext["openai.version"] as String

plugins {
	application
	id("com.google.devtools.ksp") version "1.8.20-1.0.10"
	id("io.ktor.plugin")
	kotlin("jvm")
	kotlin("plugin.serialization")
}

repositories {
	mavenCentral()
}

application {
	mainClass.set("io.github.aifiltration.ApplicationKt")

	val isDevelopment = project.ext.has("development")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
	implementation("com.aallam.openai:openai-client:$openAIVersion")
	implementation("io.ktor:ktor-client-cio-jvm:$ktorVersion")

	implementation("io.github.cdimascio:dotenv-kotlin:$dotenvVersion")

	implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinxDateTimeVersion")

	implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-sessions-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
	implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-cio-jvm:$ktorVersion")
	implementation("ch.qos.logback:logback-classic:$logbackVersion")

	platform("org.komapper:komapper-platform:$komapperVersion").let {
		implementation(it)
		ksp(it)
	}
	implementation("org.komapper:komapper-starter-r2dbc")
	implementation("org.komapper:komapper-dialect-mariadb-r2dbc")
	ksp("org.komapper:komapper-processor")
}

ksp {
	arg("komapper.metaObject", "io.github.aifiltration.database.Tables")
}
