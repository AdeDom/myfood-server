package data.models.random_user

@kotlinx.serialization.Serializable
data class Timezone(
    val description: String?,
    val offset: String?,
)