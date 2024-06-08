package model

import kotlinx.serialization.Serializable

@Serializable
data class RickAndMortyResponse<T>(
    val info: Info,
    val results: List<T> // List of generic type T
)

@Serializable
data class Info(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)
