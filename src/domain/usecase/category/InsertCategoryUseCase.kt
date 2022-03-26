package com.adedom.myfood.domain.usecase.category

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.category.CategoryRepository
import com.adedom.myfood.route.models.request.InsertCategoryRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class InsertCategoryUseCase(
    private val categoryRepository: CategoryRepository,
) {

    operator fun invoke(insertCategoryRequest: InsertCategoryRequest): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val (categoryName, image) = insertCategoryRequest
        return when {
            categoryName.isNullOrBlank() -> {
                response.error = BaseError(message = "Category name is null or blank.")
                Resource.Error(response)
            }
            image.isNullOrBlank() -> {
                response.error = BaseError(message = "Image is null or blank.")
                Resource.Error(response)
            }
            else -> {
                val isInsertCategory = categoryRepository.insertCategory(insertCategoryRequest) == 1
                if (isInsertCategory) {
                    response.status = ResponseKeyConstant.SUCCESS
                    response.result = "Insert category success."
                    Resource.Success(response)
                } else {
                    response.error = BaseError(message = "Insert category failed.")
                    Resource.Error(response)
                }
            }
        }
    }
}