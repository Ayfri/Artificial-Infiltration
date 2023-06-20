package io.github.aifiltration.api.actions

import io.github.aifiltration.types.Game
import io.github.aifiltration.types.User
import io.ktor.client.call.*
import kotlinx.datetime.Instant

suspend fun cooldown() = runCatching { get("/cooldown").body<Instant>() }

suspend fun currentGame() = runCatching { get("/games/current").body<Game>() }

suspend fun joinGame(id: Int) = runCatching { post("/games/$id/join") }

suspend fun getMembers(id: Int) = runCatching { get("/games/$id/members").body<List<User>>() }

suspend fun quitGame(id: Int) = runCatching { delete("/games/$id/quit") }
