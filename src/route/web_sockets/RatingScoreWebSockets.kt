package com.adedom.myfood.route.web_sockets

import com.adedom.myfood.data.models.request.MyRatingScoreRequest
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.domain.usecase.rating_score.MyRatingScoreUseCase
import com.adedom.myfood.utility.extension.userId
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import java.util.*

fun Route.ratingScoreWebSocketsRoute() {

    val myRatingScoreConnections = Collections.synchronizedSet<DefaultWebSocketSession>(LinkedHashSet())
    webSocket("/ws/rating/myRatingScore") {
        val myRatingScoreUseCase by closestDI().instance<MyRatingScoreUseCase>()

        myRatingScoreConnections += this
        try {
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val text = frame.readText()

                val userId = call.userId
                val myRatingScoreRequest = Json.decodeFromString<MyRatingScoreRequest>(text)
                val resource = myRatingScoreUseCase(userId, myRatingScoreRequest)
                when (resource) {
                    is Resource.Success -> {
                        val response = Json.encodeToString(resource.data)
                        myRatingScoreConnections.forEach { session ->
                            session.send(response)
                        }
                    }
                    is Resource.Error -> {
                        val response = Json.encodeToString(resource.error)
                        this.send(response)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.localizedMessage)
        } finally {
            myRatingScoreConnections -= this
        }
    }
}