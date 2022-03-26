package com.adedom.myfood.data.repositories.category

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.remote.category.CategoryRemoteDataSource
import com.adedom.myfood.route.models.request.InsertCategoryRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class CategoryRepositoryImpl(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
) : CategoryRepository {

    override fun findCategoryId(categoryId: Int): Long {
        return categoryRemoteDataSource.findCategoryId(categoryId)
    }

    override fun insertCategory(insertCategoryRequest: InsertCategoryRequest): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val isInsertCategory = categoryRemoteDataSource.insertCategory(insertCategoryRequest) == 1
        return if (isInsertCategory) {
            response.status = ResponseKeyConstant.SUCCESS
            response.result = "Insert category success."
            Resource.Success(response)
        } else {
            response.error = BaseError(message = "Insert category failed.")
            Resource.Error(response)
        }
    }
}