package io.github.aifiltration.types

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class User(val id: Int, val username: String)
