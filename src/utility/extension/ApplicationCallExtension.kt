package com.adedom.myfood.utility.extension

import com.adedom.myfood.utility.constant.RequestKeyConstant
import com.adedom.myfood.utility.jwt.JwtHelper
import com.auth0.jwt.JWT
import io.ktor.application.*
import io.ktor.request.*

val ApplicationCall.userId: String?
    get() = run {
        val authKey = request.header(RequestKeyConstant.AUTHORIZATION_KEY)
        val accessToken = authKey?.replace("Bearer", "")?.trim()
        if (!accessToken.isNullOrBlank()) {
            val userId = JWT().decodeJwt(accessToken).getClaim(JwtHelper.USER_ID).asString()
            userId
        } else {
            null
        }
    }