package com.adedom.myfood.data.repositories.random_user

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.remote.random_user.RandomUserRemoteDataSource
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import data.models.random_user.RandomUserResponse

class RandomUserRepositoryImpl(
    private val randomUserRemoteDataSource: RandomUserRemoteDataSource,
) : RandomUserRepository {

    override suspend fun getRandomUser(): Resource<BaseResponse<RandomUserResponse>> {
        val response = BaseResponse<RandomUserResponse>()

        return try {
            val getRandomUser = randomUserRemoteDataSource.getRandomUser()
            response.status = ResponseKeyConstant.SUCCESS
            response.result = getRandomUser
            Resource.Success(response)
        } catch (e: Throwable) {
            e.printStackTrace()
            response.error = BaseError(message = e.message.toString())
            Resource.Error(response)
        }
    }
}