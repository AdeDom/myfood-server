package com.adedom.myfood.route.models.entities

import org.joda.time.DateTime

data class FoodAllEntity(
    val foodId: Int,
    val foodName: String,
    val alias: String?,
    val image: String,
    val price: Double,
    val description: String?,
    val status: String,
    val created: DateTime,
    val updated: DateTime?,
    val categoryId: Int,
    val categoryName: String,
)