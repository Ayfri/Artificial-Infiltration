package io.github.aifiltration.database

import io.github.aifiltration.env
import org.komapper.jdbc.JdbcDatabase


val database = JdbcDatabase(
	url = "jdbc:mariadb://${env("DATABASE_HOST")}:${env("DATABASE_PORT")}/${env("DATABASE_NAME")}",
	user = env("DATABASE_USER"),
	password = env("DATABASE_PASSWORD")
)
