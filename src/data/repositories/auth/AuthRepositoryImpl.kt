package com.adedom.myfood.data.repositories.auth

import com.adedom.myfood.data.resouce.remote.auth.AuthRemoteDataSource
import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.LoginRequest

class AuthRepositoryImpl(
    private val dataSource: AuthRemoteDataSource,
) : AuthRepository {

    override fun callLogin(loginRequest: LoginRequest): UserEntity? {
        return dataSource.callLogin(loginRequest)
    }
}