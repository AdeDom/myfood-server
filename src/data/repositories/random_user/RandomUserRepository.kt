package com.adedom.myfood.data.repositories.random_user

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.repositories.Resource
import data.models.random_user.RandomUserResponse

interface RandomUserRepository {

    suspend fun getRandomUser(): Resource<BaseResponse<RandomUserResponse>>
}