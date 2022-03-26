package com.adedom.myfood.data.repositories.category

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.request.InsertCategoryRequest
import com.adedom.myfood.route.models.response.CategoryResponse

interface CategoryRepository {

    fun findCategoryId(categoryId: Int): Long

    fun insertCategory(insertCategoryRequest: InsertCategoryRequest): Resource<BaseResponse<String>>

    fun getCategoryAll(): Resource<BaseResponse<List<CategoryResponse>>>
}