package com.adedom.myfood.route.http

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import com.adedom.myfood.utility.extension.getAuth
import com.adedom.myfood.utility.extension.userId
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.defaultRoute() {

    get("/") {
        val messageString = "Welcome to my food."
        call.respond(HttpStatusCode.OK, messageString)
    }

    getAuth("/api/auth") {
        val userId = call.userId

        val response = BaseResponse<String>()
        response.status = ResponseKeyConstant.SUCCESS
        response.result = "Hello auth $userId"
        call.respond(HttpStatusCode.OK, response)
    }
}
