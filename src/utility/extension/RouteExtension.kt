package com.adedom.myfood.utility.extension

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.base.ErrorResponse
import com.adedom.myfood.data.resouce.local.auth.AuthLocalDataSource
import com.adedom.myfood.utility.constant.RequestKeyConstant
import com.auth0.jwt.JWT
import com.auth0.jwt.impl.PublicClaims
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

@ContextDsl
fun Route.getAuth(path: String, body: PipelineInterceptor<Unit, ApplicationCall>) {
    get(path) {
        authentication(body)
    }
}

@ContextDsl
fun Route.postAuth(path: String, body: PipelineInterceptor<Unit, ApplicationCall>) {
    post(path) {
        authentication(body)
    }
}

@ContextDsl
fun Route.putAuth(path: String, body: PipelineInterceptor<Unit, ApplicationCall>) {
    put(path) {
        authentication(body)
    }
}

@ContextDsl
fun Route.patchAuth(path: String, body: PipelineInterceptor<Unit, ApplicationCall>) {
    patch(path) {
        authentication(body)
    }
}

@ContextDsl
fun Route.deleteAuth(path: String, body: PipelineInterceptor<Unit, ApplicationCall>) {
    delete(path) {
        authentication(body)
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.authentication(body: PipelineInterceptor<Unit, ApplicationCall>) {
    val authKey = call.request.header(RequestKeyConstant.AUTHORIZATION_KEY)
    if (authKey != null) {
        val accessToken = authKey.replace("Bearer", "").trim()
        val expiresAtClaim = JWT().decodeJwt(accessToken).getClaim(PublicClaims.EXPIRES_AT).asLong()
        val currentTime = System.currentTimeMillis() / 1_000L
        val isTokenExpire = expiresAtClaim.minus(currentTime) > 0
        if (isTokenExpire) {
            val authLocalDataSource by closestDI().instance<AuthLocalDataSource>()
            val statusLoginOrRefreshCount = authLocalDataSource.findStatusLoginOrRefreshByAccessToken(accessToken)
            if (statusLoginOrRefreshCount == 1L) {
                body(Unit)
            } else {
                val response = BaseResponse<Unit>()
                val baseError = BaseError(
                    code = ErrorResponse.AccessTokenNotAvailableError.code,
                    message = ErrorResponse.AccessTokenNotAvailableError.message
                )
                response.error = baseError
                call.respond(HttpStatusCode.BadRequest, response)
            }
        } else {
            val response = BaseResponse<Unit>()
            val baseError = BaseError(
                code = ErrorResponse.AccessTokenError.code,
                message = ErrorResponse.AccessTokenError.message
            )
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