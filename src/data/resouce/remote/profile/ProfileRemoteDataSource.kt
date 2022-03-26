package com.adedom.myfood.data.resouce.remote.profile

import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.ChangeProfileRequest

interface ProfileRemoteDataSource {

    fun getUserByUserId(userId: String): UserEntity?

    fun updateUserProfile(userId: String, changeProfileRequest: ChangeProfileRequest): Int

    fun updateUserStatus(userId: String, status: String): Int
}