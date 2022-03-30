package com.adedom.myfood.data.repositories.profile

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.request.ChangeProfileRequest
import com.adedom.myfood.data.models.response.UserProfileResponse
import com.adedom.myfood.data.repositories.Resource

interface ProfileRepository {

    suspend fun userProfile(userId: String): Resource<BaseResponse<UserProfileResponse>>

    suspend fun changeProfile(
        userId: String,
        changeProfileRequest: ChangeProfileRequest
    ): Resource<BaseResponse<String>>

    suspend fun deleteAccount(userId: String): Resource<BaseResponse<String>>
}