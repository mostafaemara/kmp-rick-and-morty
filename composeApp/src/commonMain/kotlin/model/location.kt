package model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val id: Int?=null,
    val name: String,
    val type: String?=null,
    val dimension: String?=null,
    val residents: List<String>?=null,
    val url: String,
    val created: String?=null
)