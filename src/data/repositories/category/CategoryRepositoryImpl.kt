package com.adedom.myfood.data.repositories.category

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.local.category.CategoryLocalDataSource
import com.adedom.myfood.data.resouce.remote.category.CategoryRemoteDataSource
import com.adedom.myfood.route.models.base.BaseError
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.request.InsertCategoryRequest
import com.adedom.myfood.route.models.response.CategoryResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import org.joda.time.DateTime

class CategoryRepositoryImpl(
    private val categoryLocalDataSource: CategoryLocalDataSource,
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

    override fun getCategoryAll(): Resource<BaseResponse<List<CategoryResponse>>> {
        val response = BaseResponse<List<CategoryResponse>>()

        var getCategoryAll = categoryLocalDataSource.getCategoryAll()
        if (getCategoryAll.isEmpty()) {
            getCategoryAll = categoryRemoteDataSource.getCategoryAll()

            val listLocalCount = categoryLocalDataSource.insertCategoryAll(getCategoryAll)
            if (listLocalCount != getCategoryAll.size) {
                categoryLocalDataSource.deleteCategoryAll()
            }
        }

        val getCategoryAllResponse = getCategoryAll.map { categoryEntity ->
            CategoryResponse(
                categoryId = categoryEntity.categoryId,
                categoryName = categoryEntity.categoryName,
                image = categoryEntity.image,
                created = toDateTimeString(categoryEntity.created).orEmpty(),
                updated = toDateTimeString(categoryEntity.updated),
            )
        }
        response.status = ResponseKeyConstant.SUCCESS
        response.result = getCategoryAllResponse
        return Resource.Success(response)
    }

    private fun toDateTimeString(dateTime: DateTime?): String? {
        return dateTime?.toString("d/M/yyyy H:m")
    }
}