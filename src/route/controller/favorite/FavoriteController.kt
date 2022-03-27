package com.adedom.myfood.route.controller.favorite

import com.adedom.myfood.data.resouce.local.favorite.FavoriteLocalDataSource
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.response.FavoriteResponse
import com.adedom.myfood.utility.constant.AppConstant
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import com.adedom.myfood.utility.extension.postAuth
import com.adedom.myfood.utility.userId
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.joda.time.DateTime
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import java.util.*

fun Route.favoriteRoute() {

    get("/api/favorite/getFavoriteAll") {
        val favoriteLocalDataSource by closestDI().instance<FavoriteLocalDataSource>()

        val response = BaseResponse<List<FavoriteResponse>>()
        val favoriteEntityList = favoriteLocalDataSource.getFavoriteAll()
        val favoriteResponseList = favoriteEntityList.map { favoriteEntity ->
            FavoriteResponse(
                favoriteId = favoriteEntity.favoriteId,
                userId = favoriteEntity.userId,
                foodId = favoriteEntity.foodId,
                backupState = favoriteEntity.backupState,
                created = favoriteEntity.created,
                updated = favoriteEntity.updated,
            )
        }
        response.result = favoriteResponseList
        response.status = ResponseKeyConstant.SUCCESS
        call.respond(HttpStatusCode.OK, response)
    }

    postAuth("/api/favorite/myFavorite/{foodId}") {
        val favoriteLocalDataSource by closestDI().instance<FavoriteLocalDataSource>()

        val favoriteId = UUID.randomUUID().toString().replace("-", "")
        val userId = call.userId
        val foodId = call.parameters["foodId"]?.toInt() ?: 0
        val state = AppConstant.LOCAL_BACKUP
        val currentDateTime = DateTime(System.currentTimeMillis() + AppConstant.DATE_TIME_THAI)
        val created = currentDateTime.toString("yyyy/M/d H:m")
        favoriteLocalDataSource.myFavorite(favoriteId, userId, foodId, state, created)

        val response = BaseResponse<String>()
        response.result = "Favorite is success."
        response.status = ResponseKeyConstant.SUCCESS
        call.respond(HttpStatusCode.OK, response)
    }

    delete("/api/favorite/deleteAll") {
        val favoriteLocalDataSource by closestDI().instance<FavoriteLocalDataSource>()

        favoriteLocalDataSource.deleteFavoriteAll()

        val response = BaseResponse<String>()
        response.result = "Delete favorite all success."
        response.status = ResponseKeyConstant.SUCCESS
        call.respond(HttpStatusCode.OK, response)
    }
}