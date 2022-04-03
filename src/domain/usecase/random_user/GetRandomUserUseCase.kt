package com.adedom.myfood.domain.usecase.random_user

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.random_user.RandomUserRepository
import data.models.random_user.RandomUserResponse

class GetRandomUserUseCase(
    private val randomUserRepository: RandomUserRepository,
) {

    suspend operator fun invoke(): Resource<BaseResponse<RandomUserResponse>> {
        return when {
            else -> {
                randomUserRepository.getRandomUser()
            }
        }
    }
}