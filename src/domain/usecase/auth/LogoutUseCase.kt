package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.auth.AuthRepository
import com.adedom.myfood.route.models.base.BaseResponse

class LogoutUseCase(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(): Resource<BaseResponse<String>> {
        return when {
            else -> {
                authRepository.logout()
            }
        }
    }
}