package com.adedom.myfood.route.models.entities

@kotlinx.serialization.Serializable
data class MyFoodEntity(
    val id: Int,
    val foodDefault: String,
)