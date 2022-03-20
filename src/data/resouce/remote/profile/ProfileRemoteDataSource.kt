package com.adedom.myfood.data.resouce.remote.profile

import com.adedom.myfood.route.models.entities.UserEntity

interface ProfileRemoteDataSource {

    fun getUserByUserId(userId: String): UserEntity

    fun updateUserStatus(userId: String, status: String): Int
}