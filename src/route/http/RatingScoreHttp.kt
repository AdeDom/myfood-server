package com.adedom.myfood.route.http

import com.adedom.myfood.data.models.request.MyRatingScoreRequest
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.domain.usecase.rating_score.DeleteRatingScoreAllUseCase
import com.adedom.myfood.domain.usecase.rating_score.GetRatingScoreAllUseCase
import com.adedom.myfood.domain.usecase.rating_score.MyRatingScoreUseCase
import com.adedom.myfood.domain.usecase.rating_score.SyncDataRatingScoreUseCase
import com.adedom.myfood.utility.constant.RequestKeyConstant
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.ratingScoreRoute() {

    get("/api/rating/getRatingScoreAll") {
        val getRatingScoreAllUseCase by closestDI().instance<GetRatingScoreAllUseCase>()

        val resource = getRatingScoreAllUseCase()
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }

    post("/api/rating/myRatingScore") {
        val myRatingScoreUseCase by closestDI().instance<MyRatingScoreUseCase>()

        val authKey = call.request.header(RequestKeyConstant.AUTHORIZATION_KEY)
        val myRatingScoreRequest = call.receive<MyRatingScoreRequest>()
        val resource = myRatingScoreUseCase(authKey, myRatingScoreRequest)
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }

    delete("/api/rating/deleteAll") {
        val deleteRatingScoreAllUseCase by closestDI().instance<DeleteRatingScoreAllUseCase>()

        val resource = deleteRatingScoreAllUseCase()
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }

    post("/api/rating/syncDataRatingScore") {
        val syncDataRatingScoreUseCase by closestDI().instance<SyncDataRatingScoreUseCase>()

        val resource = syncDataRatingScoreUseCase()
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