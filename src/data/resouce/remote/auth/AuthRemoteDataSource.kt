package com.adedom.myfood.data.resouce.remote.auth

import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.LoginRequest

interface AuthRemoteDataSource {

    fun callLogin(loginRequest: LoginRequest): UserEntity?
}