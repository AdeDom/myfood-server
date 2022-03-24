package com.adedom.myfood.data.resouce.remote.category

import com.adedom.myfood.route.models.request.InsertCategoryRequest

interface CategoryRemoteDataSource {

    fun insertCategory(insertCategoryRequest: InsertCategoryRequest): Int?
}