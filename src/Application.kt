package com.adedom.myfood

import com.adedom.myfood.di.domainModule
import com.adedom.myfood.di.repositoryModule
import com.adedom.myfood.route.controller.default.defaultRoute
import com.adedom.myfood.route.controller.food.foodRoute
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json
import org.kodein.di.ktor.di

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
fun Application.module() {

    // start project
    install(DefaultHeaders)
    install(CallLogging)

    // route location
    install(Locations)

    // gson convertor json
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    // kodein dependencies injection
    di {
        importAll(
            repositoryModule,
            domainModule,
        )
    }

    // route
    install(Routing) {
        defaultRoute()
        foodRoute()
    }
}