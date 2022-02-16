package com.adedom.myfood

import com.adedom.myfood.di.databaseModule
import com.adedom.myfood.di.domainModule
import com.adedom.myfood.di.remoteDataSourceModule
import com.adedom.myfood.di.repositoryModule
import com.adedom.myfood.route.controller.default.defaultRoute
import com.adedom.myfood.route.controller.food.foodRoute
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.kodein.di.ktor.di

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
fun Application.module() {
    // database mysql
    val usernameEnv = environment.config.property("my_food_db.username").getString()
    val passwordEnv = environment.config.property("my_food_db.password").getString()
    val jdbcUrlEnv = environment.config.property("my_food_db.jdbc_url").getString()
    val config = HikariConfig().apply {
        jdbcUrl = jdbcUrlEnv
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username = usernameEnv
        password = passwordEnv
        maximumPoolSize = 10
    }
    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)

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
            databaseModule,
            remoteDataSourceModule,
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