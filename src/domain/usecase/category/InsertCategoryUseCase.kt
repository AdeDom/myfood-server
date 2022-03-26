package com.adedom.myfood.domain.usecase.category

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.category.CategoryRepository
import com.adedom.myfood.route.models.request.InsertCategoryRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse

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
                categoryRepository.insertCategory(insertCategoryRequest)
            }
        }
    }
}