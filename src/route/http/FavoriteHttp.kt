package com.adedom.myfood.route.http

import com.adedom.myfood.data.models.request.MyFavoriteRequest
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.domain.usecase.favorite.DeleteFavoriteAllUseCase
import com.adedom.myfood.domain.usecase.favorite.GetFavoriteAllUseCase
import com.adedom.myfood.domain.usecase.favorite.MyFavoriteUseCase
import com.adedom.myfood.domain.usecase.favorite.SyncDataFavoriteUseCase
import com.adedom.myfood.utility.extension.postAuth
import com.adedom.myfood.utility.extension.userId
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.favoriteRoute() {

    get("/api/favorite/getFavoriteAll") {
        val getFavoriteAllUseCase by closestDI().instance<GetFavoriteAllUseCase>()

        val resource = getFavoriteAllUseCase()
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }

    postAuth("/api/favorite/myFavorite") {
        val myFavoriteUseCase by closestDI().instance<MyFavoriteUseCase>()

        val userId = call.userId
        val myFavoriteRequest = call.receive<MyFavoriteRequest>()
        val resource = myFavoriteUseCase(userId, myFavoriteRequest)
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }

    delete("/api/favorite/deleteAll") {
        val deleteFavoriteAllUseCase by closestDI().instance<DeleteFavoriteAllUseCase>()

        val resource = deleteFavoriteAllUseCase()
        when (resource) {
            is Resource.Success -> {
                call.respond(HttpStatusCode.OK, resource.data)
            }
            is Resource.Error -> {
                call.respond(HttpStatusCode.BadRequest, resource.error)
            }
        }
    }

    post("/api/favorite/syncDataFavorite") {
        val syncDataFavoriteUseCase by closestDI().instance<SyncDataFavoriteUseCase>()

        val resource = syncDataFavoriteUseCase()
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