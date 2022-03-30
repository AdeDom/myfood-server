package com.adedom.myfood.domain.usecase.profile

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.UserProfileResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.profile.ProfileRepository

class UserProfileUseCase(
    private val profileRepository: ProfileRepository,
) {

    suspend operator fun invoke(userId: String): Resource<BaseResponse<UserProfileResponse>> {
        return when {
            else -> {
                profileRepository.userProfile(userId)
            }
        }
    }
}