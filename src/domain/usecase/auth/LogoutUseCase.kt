package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.auth.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(userId: String): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        return when {
            userId.isBlank() -> {
                response.error = BaseError(message = "User id is blank.")
                Resource.Error(response)
            }
            else -> {
                authRepository.logout(userId)
            }
        }
    }
}