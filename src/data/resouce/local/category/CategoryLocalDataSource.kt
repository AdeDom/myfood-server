package com.adedom.myfood.data.resouce.local.category

import com.adedom.myfood.route.models.entities.CategoryEntity

interface CategoryLocalDataSource {

  suspend fun getCategoryAll(): List<CategoryEntity>

    suspend fun insertCategoryAll(categoryList: List<CategoryEntity>): Int

    suspend fun deleteCategoryAll(): Int
}