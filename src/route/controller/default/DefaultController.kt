package com.adedom.myfood.route.controller.default

import com.adedom.myfood.route.models.request.DefaultRequest
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
fun Route.defaultRoute() {

    get<DefaultRequest> {
        val messageString = "Welcome to my food."
        call.respond(HttpStatusCode.OK, messageString)
    }
}