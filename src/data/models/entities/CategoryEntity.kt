package com.adedom.myfood.data.models.entities

import org.joda.time.DateTime

data class CategoryEntity(
    val categoryId: Int,
    val categoryName: String,
    val image: String,
    val categoryTypeName: String,
    val created: DateTime,
    val updated: DateTime?,
)