package io.github.aifiltration.api.actions

import io.github.aifiltration.types.Game
import io.ktor.client.call.*

suspend fun currentGame() = runCatching { get("/games/current").body<Game>() }

suspend fun joinGame(id: Int) = runCatching { post("/games/$id/join") }
