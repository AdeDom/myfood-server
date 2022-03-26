package com.adedom.myfood.domain.usecase.category

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.category.CategoryRepository
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.route.models.response.base.CategoryResponse

class GetCategoryAllUseCase(
    private val categoryRepository: CategoryRepository,
) {

    operator fun invoke(): Resource<BaseResponse<List<CategoryResponse>>> {
        return when {
            else -> {
                categoryRepository.getCategoryAll()
            }
        }
    }
}