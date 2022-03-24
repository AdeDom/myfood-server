package com.adedom.myfood.data.resouce.remote.category

import com.adedom.myfood.route.models.request.InsertCategoryRequest

interface CategoryRemoteDataSource {

    fun findCategoryId(categoryId: Int): Long

    fun insertCategory(insertCategoryRequest: InsertCategoryRequest): Int?
}