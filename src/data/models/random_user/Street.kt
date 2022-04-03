package data.models.random_user

@kotlinx.serialization.Serializable
data class Street(
    val name: String?,
    val number: Int?,
)