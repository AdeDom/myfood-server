package com.adedom.myfood.data.repositories.profile

import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.ChangeProfileRequest

interface ProfileRepository {

    fun getUserProfile(userId: String): UserEntity

    fun updateUserProfile(userId: String, changeProfileRequest: ChangeProfileRequest): Int

    fun updateUserStatusInActive(userId: String): Int
}