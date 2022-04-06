package com.adedom.myfood.route.web_sockets

import com.adedom.myfood.data.models.request.MyFavoriteRequest
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.domain.usecase.favorite.MyFavoriteUseCase
import com.adedom.myfood.utility.extension.userId
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import java.util.*

fun Route.favoriteWebSocketsRoute() {

    val myFavoriteConnections = Collections.synchronizedSet<DefaultWebSocketSession>(LinkedHashSet())
    webSocket("/ws/favorite/myFavorite") {
        val myFavoriteUseCase by closestDI().instance<MyFavoriteUseCase>()

        myFavoriteConnections += this
        try {
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val text = frame.readText()

                val userId = call.userId
                val myFavoriteRequest = Json.decodeFromString<MyFavoriteRequest>(text)
                val resource = myFavoriteUseCase(userId, myFavoriteRequest)
                when (resource) {
                    is Resource.Success -> {
                        val response = Json.encodeToString(resource.data)
                        myFavoriteConnections.forEach { session ->
                            session.send(response)
                        }
                    }
                    is Resource.Error -> {
                        val response = Json.encodeToString(resource.error)
                        this.send(response)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.localizedMessage)
        } finally {
            myFavoriteConnections -= this
        }
    }
}