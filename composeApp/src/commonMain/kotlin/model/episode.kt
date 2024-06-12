package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    val id: Int,
    val name: String,
    @SerialName("air-date") val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)