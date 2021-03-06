package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.request.ChangePasswordRequest
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.auth.AuthRepository

class ChangePasswordUseCase(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(
        userId: String?,
        changePasswordRequest: ChangePasswordRequest
    ): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val (oldPassword, newPassword) = changePasswordRequest
        return when {
            userId.isNullOrBlank() -> {
                response.error = BaseError(message = "User id is null or blank.")
                Resource.Error(response)
            }
            oldPassword.isNullOrBlank() -> {
                response.error = BaseError(message = "Old password is null or blank.")
                Resource.Error(response)
            }
            newPassword.isNullOrBlank() -> {
                response.error = BaseError(message = "New password is null or blank.")
                Resource.Error(response)
            }
            isPasswordInvalid(userId, oldPassword) -> {
                response.error = BaseError(message = "The old password is invalid.")
                Resource.Error(response)
            }
            else -> {
                authRepository.changePassword(userId, newPassword)
            }
        }
    }

    private suspend fun isPasswordInvalid(userId: String, oldPassword: String): Boolean {
        return authRepository.findUserByUserIdAndPassword(userId, oldPassword) == 0L
    }
}