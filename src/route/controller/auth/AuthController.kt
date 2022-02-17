package com.adedom.myfood.route.controller.auth

import com.adedom.myfood.domain.usecase.Resource
import com.adedom.myfood.domain.usecase.auth.LoginUseCase
import com.adedom.myfood.route.models.request.LoginRequest
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.authRoute() {

    post("/api/auth/login") {
        val loginUseCase by closestDI().instance<LoginUseCase>()

        val request = call.receive<LoginRequest>()
        val resource = loginUseCase(request)
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }
}