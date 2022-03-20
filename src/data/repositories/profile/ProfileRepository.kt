package com.adedom.myfood.data.repositories.profile

import com.adedom.myfood.route.models.entities.UserEntity

interface ProfileRepository {

    fun getUserProfile(userId: String): UserEntity

    fun updateUserStatusInActive(userId: String): Int
}