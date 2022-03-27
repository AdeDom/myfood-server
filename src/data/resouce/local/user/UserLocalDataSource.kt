package com.adedom.myfood.data.resouce.local.user

import com.adedom.myfood.route.models.entities.UserEntity

interface UserLocalDataSource {

    suspend fun getUserByUserId(userId: String): UserEntity?

    suspend fun getUserAll(): List<UserEntity>

    suspend fun insertUserAll(userList: List<UserEntity>): Int

    suspend fun deleteUserAll(): Int
}