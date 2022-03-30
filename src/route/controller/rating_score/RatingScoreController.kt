package com.adedom.myfood.route.controller.rating_score

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.domain.usecase.rating_score.DeleteRatingScoreAllUseCase
import com.adedom.myfood.domain.usecase.rating_score.GetRatingScoreAllUseCase
import com.adedom.myfood.domain.usecase.rating_score.MyRatingScoreUseCase
import com.adedom.myfood.domain.usecase.rating_score.SyncDataRatingScoreUseCase
import com.adedom.myfood.utility.extension.postAuth
import com.adedom.myfood.utility.extension.userId
import io.ktor.application.*
import io.ktor.http.*
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

    postAuth("/api/rating/myRatingScore/{foodId}/{ratingScore}") {
        val myRatingScoreUseCase by closestDI().instance<MyRatingScoreUseCase>()

        val userId = call.userId
        val foodId = call.parameters["foodId"]
        val ratingScore = call.parameters["ratingScore"]
        val resource = myRatingScoreUseCase(userId, foodId, ratingScore)
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