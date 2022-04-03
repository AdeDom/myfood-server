package data.models.random_user

@kotlinx.serialization.Serializable
data class Coordinates(
    val latitude: String?,
    val longitude: String?,
)