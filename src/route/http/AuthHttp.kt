package com.adedom.myfood.route.http

import com.adedom.myfood.data.models.request.ChangePasswordRequest
import com.adedom.myfood.data.models.request.LoginRequest
import com.adedom.myfood.data.models.request.RegisterRequest
import com.adedom.myfood.data.models.request.TokenRequest
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.domain.usecase.auth.*
import com.adedom.myfood.utility.extension.postAuth
import com.adedom.myfood.utility.extension.putAuth
import com.adedom.myfood.utility.extension.userId
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

    post("/api/auth/refreshToken") {
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

    postAuth("/api/auth/logout") {
        val logoutUseCase by closestDI().instance<LogoutUseCase>()

        val userId = call.userId
        val resource = logoutUseCase(userId)
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }

    putAuth("/api/auth/changePassword") {
        val changePasswordUseCase by closestDI().instance<ChangePasswordUseCase>()

        val userId = call.userId
        val request = call.receive<ChangePasswordRequest>()
        val resource = changePasswordUseCase(userId, request)
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }

    post("/api/auth/syncDataAuth") {
        val syncDataAuthUseCase by closestDI().instance<SyncDataAuthUseCase>()

        val resource = syncDataAuthUseCase()
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