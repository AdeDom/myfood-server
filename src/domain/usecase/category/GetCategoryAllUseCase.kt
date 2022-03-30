package com.adedom.myfood.domain.usecase.category

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.CategoryResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.category.CategoryRepository

class GetCategoryAllUseCase(
    private val categoryRepository: CategoryRepository,
) {

    suspend operator fun invoke(): Resource<BaseResponse<List<CategoryResponse>>> {
        return when {
            else -> {
                categoryRepository.getCategoryAll()
            }
        }
    }
}