package com.adedom.myfood.plugins

import io.ktor.server.application.*
import io.ktor.server.websocket.*

fun Application.configureWebSockets() {
    install(WebSockets)
}