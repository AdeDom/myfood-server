package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.repositories.auth.AuthRepository
import com.adedom.myfood.domain.usecase.Resource
import com.adedom.myfood.route.models.request.LoginRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.route.models.response.base.TokenResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import com.adedom.myfood.utility.jwt.JwtHelper
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginUseCase(
    private val jwtHelper: JwtHelper,
    private val authRepository: AuthRepository,
) {

    operator fun invoke(loginRequest: LoginRequest): Resource<BaseResponse<TokenResponse>> {
        val response = BaseResponse<TokenResponse>()

        val (username, password) = loginRequest
        return when {
            username.isNullOrBlank() -> {
                response.error = BaseError(code = "001", message = "Username is null or blank.")
                Resource.Error(response)
            }
            password.isNullOrBlank() -> {
                response.error = BaseError(code = "002", message = "Password is null or blank.")
                Resource.Error(response)
            }
            else -> {
                val userEntity = authRepository.callLogin(loginRequest.copy(password = encryptSHA(password)))
                if (userEntity != null) {
                    response.status = ResponseKeyConstant.SUCCESS
                    response.result = TokenResponse(
                        accessToken = jwtHelper.encodeAccessToken(userEntity.userId),
                        refreshToken = jwtHelper.encodeRefreshToken(userEntity.userId)
                    )
                    Resource.Success(response)
                } else {
                    response.error = BaseError(code = "003", message = "Username or password incorrect.")
                    Resource.Error(response)
                }
            }
        }
    }

    private fun encryptSHA(password: String): String {
        var sha = ""
        try {
            val messageDigest = MessageDigest.getInstance("SHA-512")
            val byteArray = messageDigest.digest(password.toByteArray())
            val bigInteger = BigInteger(1, byteArray)
            sha = bigInteger.toString(16).padStart(64, '0')
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return sha
    }
}