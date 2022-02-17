package com.adedom.myfood.data.repositories.auth

import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.LoginRequest

interface AuthRepository {

    fun callLogin(loginRequest: LoginRequest): UserEntity?
}