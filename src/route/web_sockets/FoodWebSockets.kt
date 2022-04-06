package com.adedom.myfood.route.web_sockets

import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import java.util.*

fun Route.foodWebSocketsRoute() {

    val connections = Collections.synchronizedSet<DefaultWebSocketSession>(LinkedHashSet())
    webSocket("/ws/food/favoriteAndRatingScore") {
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