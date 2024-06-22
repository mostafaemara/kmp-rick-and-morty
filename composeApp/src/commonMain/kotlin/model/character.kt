package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url:String,
    val created:String
)

@Serializable
enum class  CharacterStatus {
                    @SerialName("Alive")
                    ALIVE,
    @SerialName("Dead")
    DEAD,
    @SerialName("unknown")
    UNKNOWN
}