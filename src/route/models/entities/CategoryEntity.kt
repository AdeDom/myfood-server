package com.adedom.myfood.route.models.entities

import org.joda.time.DateTime

data class CategoryEntity(
    val categoryId: Int,
    val categoryName: String,
    val image: String,
    val created: DateTime,
    val updated: DateTime?,
)