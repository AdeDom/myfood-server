package com.adedom.myfood.domain.usecase.profile

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.profile.ProfileRepository
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class DeleteAccountUseCase(
    private val profileRepository: ProfileRepository,
) {

    operator fun invoke(userId: String): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        return when {
            userId.isBlank() -> {
                response.error = BaseError(message = "User id is blank.")
                Resource.Error(response)
            }
            else -> {
                val isUpdateStatus = profileRepository.updateUserStatusInActive(userId) == 1
                if (isUpdateStatus) {
                    response.status = ResponseKeyConstant.SUCCESS
                    response.result = "Delete account successfully."
                    Resource.Success(response)
                } else {
                    response.error = BaseError(message = "Delete account failed.")
                    Resource.Error(response)
                }
            }
        }
    }
}