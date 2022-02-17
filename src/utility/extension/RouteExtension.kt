package com.adedom.myfood.utility.extension

import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.utility.constant.RequestKeyConstant
import com.auth0.jwt.JWT
import com.auth0.jwt.impl.PublicClaims
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*

fun Route.getAuth(path: String, body: PipelineInterceptor<Unit, ApplicationCall>) {
    get(path) {
        authentication(body)
    }
}

fun Route.postAuth(path: String, body: PipelineInterceptor<Unit, ApplicationCall>) {
    post(path) {
        authentication(body)
    }
}

fun Route.putAuth(path: String, body: PipelineInterceptor<Unit, ApplicationCall>) {
    put(path) {
        authentication(body)
    }
}

fun Route.patchAuth(path: String, body: PipelineInterceptor<Unit, ApplicationCall>) {
    patch(path) {
        authentication(body)
    }
}

fun Route.deleteAuth(path: String, body: PipelineInterceptor<Unit, ApplicationCall>) {
    delete(path) {
        authentication(body)
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.authentication(body: PipelineInterceptor<Unit, ApplicationCall>) {
    val authKey = call.request.header(RequestKeyConstant.AUTHORIZATION_KEY)
    if (authKey != null) {
        val expiresAtClaim = JWT().decodeJwt(authKey).getClaim(PublicClaims.EXPIRES_AT).asLong()
        val currentTime = System.currentTimeMillis() / 1_000L
        val isTokenExpire = expiresAtClaim.minus(currentTime) > 0
        if (isTokenExpire) {
            body(Unit)
        } else {
            val response = BaseResponse<Unit>()
            val baseError = BaseError(code = "401-001", message = "Access token expire.")
            response.error = baseError
            call.respond(HttpStatusCode.Unauthorized, response)
        }
    } else {
        val response = BaseResponse<Unit>()
        val baseError = BaseError(
            code = HttpStatusCode.Unauthorized.value.toString(),
            message = HttpStatusCode.Unauthorized.description
        )
        response.error = baseError
        call.respond(HttpStatusCode.Unauthorized, response)
    }
}