package data.models.random_user

@kotlinx.serialization.Serializable
data class Dob(
    val age: Int?,
    val date: String?,
)