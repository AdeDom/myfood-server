package com.adedom.myfood.data.models.base

sealed class ErrorResponse(
    val code: String,
    val message: String,
) {
    object AccessTokenError : ErrorResponse(code = "401-001", message = "Access token expire.")
    object RefreshTokenError : ErrorResponse(code = "401-002", message = "Refresh token expire.")
}