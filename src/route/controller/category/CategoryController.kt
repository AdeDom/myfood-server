package com.adedom.myfood.route.controller.category

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.domain.usecase.category.GetCategoryAllUseCase
import com.adedom.myfood.domain.usecase.category.InsertCategoryUseCase
import com.adedom.myfood.route.models.request.InsertCategoryRequest
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.categoryRoute() {

    post("/api/category/insert") {
        val insertCategoryUseCase by closestDI().instance<InsertCategoryUseCase>()

        val request = call.receive<InsertCategoryRequest>()
        val resource = insertCategoryUseCase(request)
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }

    get("/api/category/getCategoryAll") {
        val getCategoryAllUseCase by closestDI().instance<GetCategoryAllUseCase>()

        val resource = getCategoryAllUseCase()
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