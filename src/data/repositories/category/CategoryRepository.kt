package com.adedom.myfood.data.repositories.category

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.request.InsertCategoryRequest
import com.adedom.myfood.route.models.response.CategoryResponse

interface CategoryRepository {

    suspend fun findCategoryId(categoryId: Int): Long

    suspend fun insertCategory(insertCategoryRequest: InsertCategoryRequest): Resource<BaseResponse<String>>

    suspend fun getCategoryAll(): Resource<BaseResponse<List<CategoryResponse>>>
}