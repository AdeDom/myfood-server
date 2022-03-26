package com.adedom.myfood.data.resouce.remote.category

import com.adedom.myfood.route.models.request.InsertCategoryRequest
import com.adedom.myfood.route.models.response.base.CategoryEntity

interface CategoryRemoteDataSource {

    fun findCategoryId(categoryId: Int): Long

    fun insertCategory(insertCategoryRequest: InsertCategoryRequest): Int?

    fun getCategoryAll(): List<CategoryEntity>
}