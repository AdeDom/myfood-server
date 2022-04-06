package com.adedom.myfood.domain.usecase.profile

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.UserProfileResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.profile.ProfileRepository

class UserProfileUseCase(
    private val profileRepository: ProfileRepository,
) {

    suspend operator fun invoke(userId: String?): Resource<BaseResponse<UserProfileResponse>> {
        val response = BaseResponse<UserProfileResponse>()

        return when {
            userId.isNullOrBlank() -> {
                response.error = BaseError(message = "User id is null or blank.")
                Resource.Error(response)
            }
            else -> {
                profileRepository.userProfile(userId)
            }
        }
    }
}