package com.adedom.myfood.data.resouce.remote.category

import com.adedom.myfood.route.models.entities.CategoryEntity
import com.adedom.myfood.route.models.request.InsertCategoryRequest

interface CategoryRemoteDataSource {

    suspend fun findCategoryId(categoryId: Int): Long

    suspend fun insertCategory(insertCategoryRequest: InsertCategoryRequest): Int?

    suspend fun getCategoryAll(): List<CategoryEntity>
}