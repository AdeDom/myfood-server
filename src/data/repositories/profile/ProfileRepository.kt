package com.adedom.myfood.data.repositories.profile

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.ChangeProfileRequest
import com.adedom.myfood.route.models.response.base.BaseResponse

interface ProfileRepository {

    fun getUserProfile(userId: String): UserEntity

    fun changeProfile(userId: String, changeProfileRequest: ChangeProfileRequest): Resource<BaseResponse<String>>

    fun deleteAccount(userId: String): Resource<BaseResponse<String>>
}