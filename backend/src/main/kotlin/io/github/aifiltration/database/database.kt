package io.github.aifiltration.database

import io.github.aifiltration.env
import org.komapper.r2dbc.R2dbcDatabase

object Tables

private val user = env("DATABASE_USER")
private val password = env("DATABASE_PASSWORD")
private val host = env("DATABASE_HOST")
private val port = env("DATABASE_PORT")
private val name = env("DATABASE_NAME")

/**
 * **Database URL format:**
 * ```
 * r2dbc:a-driver:pipes://localhost:3306/my_database?locale=en_US
 * \___/ \______/ \___/   \____________/\__________/\___________/
 *  |       |       |           |            |           |
 * scheme driver  protocol  authority       path       query
 * ```
 */
val database = R2dbcDatabase("r2dbc:mariadb://$user:$password@$host:$port/$name")
