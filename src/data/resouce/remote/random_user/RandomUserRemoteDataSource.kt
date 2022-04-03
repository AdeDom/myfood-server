package com.adedom.myfood.data.resouce.remote.random_user

import data.models.random_user.RandomUserResponse

interface RandomUserRemoteDataSource {

    suspend fun getRandomUser(): RandomUserResponse
}