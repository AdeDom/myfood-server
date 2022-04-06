package com.adedom.myfood.data.models.request

@kotlinx.serialization.Serializable
data class MyFavoriteRequest(
    val foodId: Int?,
)