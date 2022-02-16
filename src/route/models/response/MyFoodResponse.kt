package com.adedom.myfood.route.models.response

import kotlinx.serialization.Serializable

@Serializable
data class MyFoodResponse(
    val message: String,
)