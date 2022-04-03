package data.models.random_user

@kotlinx.serialization.Serializable
data class RandomUserResponse(
    val info: Info?,
    val results: List<Result> = emptyList(),
)