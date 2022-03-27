package com.adedom.myfood.data.repositories.profile

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.request.ChangeProfileRequest
import com.adedom.myfood.route.models.response.UserProfileResponse

interface ProfileRepository {

    suspend fun userProfile(userId: String): Resource<BaseResponse<UserProfileResponse>>

    suspend fun changeProfile(
        userId: String,
        changeProfileRequest: ChangeProfileRequest
    ): Resource<BaseResponse<String>>

    suspend fun deleteAccount(userId: String): Resource<BaseResponse<String>>
}