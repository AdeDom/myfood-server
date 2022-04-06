package com.adedom.myfood

import com.adedom.myfood.data.database.h2.H2Database
import com.adedom.myfood.data.database.h2.H2DatabaseImpl
import com.adedom.myfood.data.database.mysql.MySqlDatabase
import com.adedom.myfood.data.database.mysql.MySqlDatabaseConfig
import com.adedom.myfood.data.database.mysql.MySqlDatabaseImpl
import com.adedom.myfood.data.database.sqlite.SqliteDatabase
import com.adedom.myfood.data.database.sqlite.SqliteDatabaseImpl
import com.adedom.myfood.di.domainModule
import com.adedom.myfood.di.localDataSourceModule
import com.adedom.myfood.di.remoteDataSourceModule
import com.adedom.myfood.di.repositoryModule
import com.adedom.myfood.route.http.*
import com.adedom.myfood.route.web_sockets.chatWebSocketsRoute
import com.adedom.myfood.route.web_sockets.favoriteWebSocketsRoute
import com.adedom.myfood.utility.jwt.JwtConfig
import com.adedom.myfood.utility.jwt.JwtHelper
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.websocket.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@ExperimentalSerializationApi
fun Application.module() {
    // start project
    install(DefaultHeaders)
    install(CallLogging)

    // Json convertor
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

    // Web sockets
    install(WebSockets)

    val databaseNameEnv = environment.config.property("my_food_db.database_name").getString()
    val usernameEnv = environment.config.property("my_food_db.username").getString()
    val passwordEnv = environment.config.property("my_food_db.password").getString()
    val jdbcUrlEnv = environment.config.property("my_food_db.jdbc_url").getString()

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    // kodein dependencies injection
    di {
        // database
        bindSingleton {
            MySqlDatabaseConfig(
                databaseName = databaseNameEnv,
                username = usernameEnv,
                password = passwordEnv,
                jdbcUrl = jdbcUrlEnv,
            )
        }
        bindSingleton<MySqlDatabase> { MySqlDatabaseImpl(instance()) }
        bindSingleton<SqliteDatabase> { SqliteDatabaseImpl() }
        bindSingleton<H2Database> { H2DatabaseImpl() }

        // structure
        importAll(
            localDataSourceModule,
            remoteDataSourceModule,
            repositoryModule,
            domainModule,
        )

        // jwt
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
        categoryRoute()
        favoriteRoute()
        ratingScoreRoute()
        randomUserRoute()

        chatWebSocketsRoute()
        favoriteWebSocketsRoute()
    }
}