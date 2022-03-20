package com.adedom.myfood

import com.adedom.myfood.di.domainModule
import com.adedom.myfood.di.remoteDataSourceModule
import com.adedom.myfood.di.repositoryModule
import com.adedom.myfood.route.controller.auth.authRoute
import com.adedom.myfood.route.controller.default.defaultRoute
import com.adedom.myfood.route.controller.food.foodRoute
import com.adedom.myfood.route.controller.profile.profileRoute
import com.adedom.myfood.utility.jwt.JwtConfig
import com.adedom.myfood.utility.jwt.JwtHelper
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@ExperimentalSerializationApi
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

    // gson convertor json
    install(ContentNegotiation) {
        json(Json {
            encodeDefaults = true
            isLenient = true
            allowSpecialFloatingPointValues = true
            allowStructuredMapKeys = true
            prettyPrint = false
            useArrayPolymorphism = true
            explicitNulls = false
        })
    }

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    // kodein dependencies injection
    di {
        importAll(
            remoteDataSourceModule,
            repositoryModule,
            domainModule,
        )

        bindSingleton {
            JwtConfig(
                secret = secret,
                issuer = issuer,
                audience = audience,
                realm = myRealm,
            )
        }
        bindSingleton { JwtHelper(instance()) }
    }

    // route
    install(Routing) {
        defaultRoute()
        authRoute()
        profileRoute()
        foodRoute()
    }
}