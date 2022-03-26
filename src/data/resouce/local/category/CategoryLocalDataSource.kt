package com.adedom.myfood.data.resouce.local.category

import com.adedom.myfood.route.models.entities.CategoryEntity

interface CategoryLocalDataSource {

    fun getCategoryAll(): List<CategoryEntity>

    fun insertCategoryAll(categoryList: List<CategoryEntity>): Int

    fun deleteCategoryAll(): Int
}