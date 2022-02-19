package com.adedom.myfood.route.controller.auth

import com.adedom.myfood.domain.usecase.Resource
import com.adedom.myfood.domain.usecase.auth.*
import com.adedom.myfood.route.models.request.LoginRequest
import com.adedom.myfood.route.models.request.RegisterRequest
import com.adedom.myfood.route.models.request.TokenRequest
import com.adedom.myfood.utility.extension.deleteAuth
import com.adedom.myfood.utility.extension.postAuth
import com.adedom.myfood.utility.userId
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

    post("/api/auth/register") {
        val registerUseCase by closestDI().instance<RegisterUseCase>()

        val request = call.receive<RegisterRequest>()
        val resource = registerUseCase(request)
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }

    post("/api/auth/refreshtoken") {
        val refreshTokenUseCase by closestDI().instance<RefreshTokenUseCase>()

        val request = call.receive<TokenRequest>()
        val resource = refreshTokenUseCase(request)
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }

    deleteAuth("/api/auth/deleteaccount") {
        val deleteAccountUseCase by closestDI().instance<DeleteAccountUseCase>()

        val userId = call.userId
        val resource = deleteAccountUseCase(userId)
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }

    postAuth("/api/auth/logout") {
        val logoutUseCase by closestDI().instance<LogoutUseCase>()

        val resource = logoutUseCase()
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