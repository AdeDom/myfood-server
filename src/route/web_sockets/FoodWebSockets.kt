package com.adedom.myfood.route.web_sockets

import com.adedom.myfood.domain.usecase.web_sockets.GetFavoriteWebSocketsUseCase
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import java.util.*

fun Route.foodWebSocketsRoute() {

    val favoriteConnections = Collections.synchronizedSet<DefaultWebSocketSession>(LinkedHashSet())
    webSocket("/ws/food/favorite") {
        val getFavoriteWebSocketsUseCase by closestDI().instance<GetFavoriteWebSocketsUseCase>()

        val getFavoriteWebSocketsUseCaseList = getFavoriteWebSocketsUseCase()
        val getFavoriteWebSocketsUseCaseListString = Json.encodeToString(getFavoriteWebSocketsUseCaseList)
        send(getFavoriteWebSocketsUseCaseListString)

        favoriteConnections += this
        try {
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val text = frame.readText()

                favoriteConnections.forEach { session ->
                    session.send(text)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.localizedMessage)
        } finally {
            favoriteConnections -= this
        }
    }
}