package com.adedom.myfood.utility

import com.adedom.myfood.utility.constant.RequestKeyConstant
import com.adedom.myfood.utility.jwt.JwtHelper
import com.auth0.jwt.JWT
import io.ktor.application.*
import io.ktor.request.*

val ApplicationCall.userId: String
    get() = run {
        val token = request.header(RequestKeyConstant.AUTHORIZATION_KEY)
        val userId = JWT().decodeJwt(token).getClaim(JwtHelper.USER_ID).asString()
        userId
    }