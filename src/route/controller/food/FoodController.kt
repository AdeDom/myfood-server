package com.adedom.myfood.route.controller.food

import com.adedom.myfood.domain.usecase.food.MyFoodUseCase
import com.adedom.myfood.route.models.request.MyFoodRequest
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

@KtorExperimentalLocationsAPI
fun Route.foodRoute() {

    get<MyFoodRequest> {
        val myFoodUseCase by closestDI().instance<MyFoodUseCase>()
        val messageString = myFoodUseCase()
        call.respond(HttpStatusCode.OK, messageString)
    }
}