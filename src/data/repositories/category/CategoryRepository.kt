package com.adedom.myfood.data.repositories.category

import com.adedom.myfood.route.models.request.InsertCategoryRequest

interface CategoryRepository {

    fun findCategoryId(categoryId: Int): Long

    fun insertCategory(insertCategoryRequest: InsertCategoryRequest): Int?
}