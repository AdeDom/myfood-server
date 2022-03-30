package com.adedom.myfood.data.resouce.remote.profile

import com.adedom.myfood.data.models.entities.UserEntity
import com.adedom.myfood.data.models.request.ChangeProfileRequest

interface ProfileRemoteDataSource {

    suspend fun getUserByUserId(userId: String): UserEntity?

    suspend fun updateUserProfile(userId: String, changeProfileRequest: ChangeProfileRequest): Int

    suspend fun updateUserStatus(userId: String, status: String): Int
}