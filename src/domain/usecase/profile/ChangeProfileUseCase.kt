package com.adedom.myfood.domain.usecase.profile

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.profile.ProfileRepository
import com.adedom.myfood.route.models.request.ChangeProfileRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class ChangeProfileUseCase(
    private val profileRepository: ProfileRepository,
) {

    operator fun invoke(userId: String, changeProfileRequest: ChangeProfileRequest): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val (name, email, mobileNo, address) = changeProfileRequest
        return when {
            name.isNullOrBlank() -> {
                response.error = BaseError(message = "Name is null or blank.")
                Resource.Error(response)
            }
            email.isNullOrBlank() -> {
                response.error = BaseError(message = "E-mail is null or blank.")
                Resource.Error(response)
            }
            mobileNo.isNullOrBlank() -> {
                response.error = BaseError(message = "Mobile no is null or blank.")
                Resource.Error(response)
            }
            address.isNullOrBlank() -> {
                response.error = BaseError(message = "Address is null or blank.")
                Resource.Error(response)
            }
            else -> {
                val isUpdateUserProfile = profileRepository.updateUserProfile(userId, changeProfileRequest) == 1
                if (isUpdateUserProfile) {
                    response.status = ResponseKeyConstant.SUCCESS
                    response.result = "Change profile success."
                    Resource.Success(response)
                } else {
                    response.error = BaseError(message = "Change profile failed.")
                    Resource.Error(response)
                }
            }
        }
    }
}