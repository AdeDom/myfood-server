package com.adedom.myfood.domain.usecase.profile

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.profile.ProfileRepository
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.route.models.response.base.UserProfileResponse

class UserProfileUseCase(
    private val profileRepository: ProfileRepository,
) {

    operator fun invoke(userId: String): Resource<BaseResponse<UserProfileResponse>> {
        return when {
            else -> {
                profileRepository.userProfile(userId)
            }
        }
    }
}