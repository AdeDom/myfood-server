package com.adedom.myfood.route.http

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.domain.usecase.random_user.GetRandomUserUseCase
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.randomUserRoute() {

    get("/api/random/user") {
        val getRandomUserUseCase by closestDI().instance<GetRandomUserUseCase>()

        val resource = getRandomUserUseCase()
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