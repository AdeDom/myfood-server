package com.adedom.myfood.data.resouce.remote.user

import com.adedom.myfood.data.models.entities.UserEntity

interface UserRemoteDataSource {

    suspend fun getUserAll(): List<UserEntity>
}