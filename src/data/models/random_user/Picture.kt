package data.models.random_user

@kotlinx.serialization.Serializable
data class Picture(
    val large: String?,
    val medium: String?,
    val thumbnail: String?,
)