package com.adedom.myfood.plugins

import com.adedom.myfood.route.http.*
import com.adedom.myfood.route.web_sockets.chatWebSocketsRoute
import com.adedom.myfood.route.web_sockets.favoriteWebSocketsRoute
import com.adedom.myfood.route.web_sockets.ratingScoreWebSocketsRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(Routing) {
        defaultRoute()
        authRoute()
        profileRoute()
        foodRoute()
        categoryRoute()
        favoriteRoute()
        ratingScoreRoute()

        chatWebSocketsRoute()
        favoriteWebSocketsRoute()
        ratingScoreWebSocketsRoute()
    }
}