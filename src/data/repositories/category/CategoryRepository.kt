package com.adedom.myfood.data.repositories.category

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.request.InsertCategoryRequest
import com.adedom.myfood.data.models.response.CategoryResponse
import com.adedom.myfood.data.repositories.Resource

interface CategoryRepository {

    suspend fun findCategoryId(categoryId: Int): Long

    suspend fun insertCategory(insertCategoryRequest: InsertCategoryRequest): Resource<BaseResponse<String>>

    suspend fun getCategoryAll(): Resource<BaseResponse<List<CategoryResponse>>>
}