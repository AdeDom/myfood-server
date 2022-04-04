package com.adedom.myfood.route.web_sockets

import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*

fun Route.chatWebSocketsRoute() {

    webSocket("/chat") {
        send("You are connected!")
        for (frame in incoming) {
            frame as? Frame.Text ?: continue
            val receivedText = frame.readText()
            send("You said: $receivedText")
        }
    }
}