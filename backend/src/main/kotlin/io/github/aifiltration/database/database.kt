package io.github.aifiltration.database

import io.github.aifiltration.env
import org.komapper.r2dbc.R2dbcDatabase

object Tables

private val user = env("DATABASE_USER")
private val password = env("DATABASE_PASSWORD")
private val host = env("DATABASE_HOST")
private val port = env("DATABASE_PORT")
private val name = env("DATABASE_NAME")

val database = R2dbcDatabase("r2dbc:mariadb:///$user:$password@$host:$port/$name")
