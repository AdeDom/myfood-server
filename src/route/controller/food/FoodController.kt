package com.adedom.myfood.route.controller.food

import com.adedom.myfood.domain.usecase.food.MyFoodUseCase
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.foodRoute() {

    get("/api/my/food") {
        val myFoodUseCase by closestDI().instance<MyFoodUseCase>()
        val messageString = myFoodUseCase()
        call.respond(HttpStatusCode.OK, messageString)
    }
}