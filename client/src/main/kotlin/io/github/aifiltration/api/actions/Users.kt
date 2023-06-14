package io.github.aifiltration.api.actions

import io.github.aifiltration.types.User
import io.ktor.client.call.*

suspend fun me() = runCatching { get("/me").body<User>() }
