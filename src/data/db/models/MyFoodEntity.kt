package com.adedom.myfood.data.db.models

@kotlinx.serialization.Serializable
data class MyFoodEntity(
    val id: Int,
    val foodDefault: String,
)