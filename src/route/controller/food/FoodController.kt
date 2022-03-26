package com.adedom.myfood.route.controller.food

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.domain.usecase.food.InsertFoodUseCase
import com.adedom.myfood.domain.usecase.food.MyFoodUseCase
import com.adedom.myfood.route.models.request.InsertFoodRequest
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
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

    post("/api/food/insert") {
        val insertFoodUseCase by closestDI().instance<InsertFoodUseCase>()

        val request = call.receive<InsertFoodRequest>()
        val resource = insertFoodUseCase(request)
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