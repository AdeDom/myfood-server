package com.adedom.myfood.route.web_sockets

import com.adedom.myfood.domain.usecase.web_sockets.FavoriteAndRatingScoreUseCase
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import java.util.*

fun Route.foodWebSocketsRoute() {

    val connections = Collections.synchronizedSet<DefaultWebSocketSession>(LinkedHashSet())
    webSocket("/ws/food/favoriteAndRatingScore") {
        val favoriteAndRatingScoreUseCase by closestDI().instance<FavoriteAndRatingScoreUseCase>()

        val favoriteAndRatingScoreList = favoriteAndRatingScoreUseCase()
        val favoriteAndRatingScoreListString = Json.encodeToString(favoriteAndRatingScoreList)
        send(favoriteAndRatingScoreListString)

        connections += this
        try {
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val text = frame.readText()

                connections.forEach { session ->
                    session.send(text)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.localizedMessage)
        } finally {
            connections -= this
        }
    }
}