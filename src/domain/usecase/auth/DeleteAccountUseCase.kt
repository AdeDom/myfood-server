package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.repositories.auth.AuthRepository
import com.adedom.myfood.domain.usecase.Resource
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class DeleteAccountUseCase(
    private val authRepository: AuthRepository,
) {

    operator fun invoke(userId: String): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        return when {
            userId.isBlank() -> {
                response.error = BaseError(code = "001", message = "User id is blank.")
                Resource.Error(response)
            }
            else -> {
                val isUpdateStatus = authRepository.updateUserStatusInActive(userId) == 1
                if (isUpdateStatus) {
                    response.status = ResponseKeyConstant.SUCCESS
                    response.result = "Delete account successfully."
                    Resource.Success(response)
                } else {
                    response.error = BaseError(code = "002", message = "Delete account failed.")
                    Resource.Error(response)
                }
            }
        }
    }
}