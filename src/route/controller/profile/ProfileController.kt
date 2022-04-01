package com.adedom.myfood.route.controller.profile

import com.adedom.myfood.data.models.request.ChangeProfileRequest
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.domain.usecase.profile.ChangeProfileUseCase
import com.adedom.myfood.domain.usecase.profile.DeleteAccountUseCase
import com.adedom.myfood.domain.usecase.profile.UserProfileUseCase
import com.adedom.myfood.utility.extension.deleteAuth
import com.adedom.myfood.utility.extension.getAuth
import com.adedom.myfood.utility.extension.putAuth
import com.adedom.myfood.utility.extension.userId
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.profileRoute() {

    getAuth("/api/profile/user") {
        val userProfileUseCase by closestDI().instance<UserProfileUseCase>()

        val userId = call.userId
        val resource = userProfileUseCase(userId)
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }

    putAuth("/api/profile/changeProfile") {
        val changeProfileUseCase by closestDI().instance<ChangeProfileUseCase>()

        val userId = call.userId
        val request = call.receive<ChangeProfileRequest>()
        val resource = changeProfileUseCase(userId, request)
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }

    deleteAuth("/api/profile/deleteAccount") {
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
}