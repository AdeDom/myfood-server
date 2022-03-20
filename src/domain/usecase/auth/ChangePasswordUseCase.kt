package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.repositories.auth.AuthRepository
import com.adedom.myfood.domain.usecase.Resource
import com.adedom.myfood.route.models.request.ChangePasswordRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class ChangePasswordUseCase(
    private val authRepository: AuthRepository,
) {

    operator fun invoke(userId: String, changePasswordRequest: ChangePasswordRequest): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val (oldPassword, newPassword) = changePasswordRequest
        return when {
            oldPassword.isNullOrBlank() -> {
                response.error = BaseError(message = "Old password is null or blank.")
                Resource.Error(response)
            }
            newPassword.isNullOrBlank() -> {
                response.error = BaseError(message = "New password is null or blank.")
                Resource.Error(response)
            }
            else -> {
                val isOldPassword = authRepository.findUserByUserIdAndPassword(userId, oldPassword) == 1L
                if (isOldPassword) {
                    val isUpdateUserPassword = authRepository.updateUserPassword(userId, newPassword) == 1
                    if (isUpdateUserPassword) {
                        response.status = ResponseKeyConstant.SUCCESS
                        response.result = "Change password success."
                        Resource.Success(response)
                    } else {
                        response.error = BaseError(message = "Change password failed.")
                        Resource.Error(response)
                    }
                } else {
                    response.error = BaseError(message = "The old password is invalid.")
                    Resource.Error(response)
                }
            }
        }
    }
}