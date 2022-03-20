package com.adedom.myfood.domain.usecase.profile

import com.adedom.myfood.data.repositories.profile.ProfileRepository
import com.adedom.myfood.domain.usecase.Resource
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.route.models.response.base.UserProfileResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import org.joda.time.DateTime

class UserProfileUseCase(
    private val profileRepository: ProfileRepository,
) {

    operator fun invoke(userId: String): Resource<BaseResponse<UserProfileResponse>> {
        val response = BaseResponse<UserProfileResponse>()

        return when {
            else -> {
                val userEntity = profileRepository.getUserProfile(userId)
                val userProfileResponse = UserProfileResponse(
                    userId = userEntity.userId,
                    username = userEntity.username,
                    name = userEntity.name,
                    email = userEntity.email,
                    mobileNo = userEntity.mobileNo,
                    address = userEntity.address,
                    status = userEntity.status,
                    created = toDateTimeString(userEntity.created).orEmpty(),
                    updated = toDateTimeString(userEntity.updated),
                )
                response.status = ResponseKeyConstant.SUCCESS
                response.result = userProfileResponse
                Resource.Success(response)
            }
        }
    }

    private fun toDateTimeString(dateTime: DateTime?): String? {
        return dateTime?.toString("d/M/yyyy H:m")
    }
}