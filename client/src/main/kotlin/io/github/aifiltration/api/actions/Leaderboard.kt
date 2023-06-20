package io.github.aifiltration.api.actions

import io.github.aifiltration.pages.LeaderboardEntry
import io.ktor.client.call.*

suspend fun leaderboard() = runCatching { get("/leaderboard").body<List<LeaderboardEntry>>() }
