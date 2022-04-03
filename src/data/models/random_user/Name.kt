package data.models.random_user

@kotlinx.serialization.Serializable
data class Name(
    val first: String?,
    val last: String?,
    val title: String?,
)