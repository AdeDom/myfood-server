package com.adedom.myfood.data.resouce.local.category

import com.adedom.myfood.route.models.entities.CategoryEntity
import com.adedom.myfood.route.models.request.InsertCategoryRequest

interface CategoryLocalDataSource {

    fun findCategoryId(categoryId: Int): Long

    fun insertCategory(insertCategoryRequest: InsertCategoryRequest): Int?

    fun getCategoryAll(): List<CategoryEntity>

    fun insertCategoryAll(categoryList: List<CategoryEntity>): Int

    fun deleteCategoryAll(): Int
}