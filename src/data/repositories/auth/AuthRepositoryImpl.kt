package com.adedom.myfood.data.repositories.auth

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.remote.auth.AuthRemoteDataSource
import com.adedom.myfood.route.models.base.BaseError
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.request.RegisterRequest
import com.adedom.myfood.route.models.response.TokenResponse
import com.adedom.myfood.utility.constant.AppConstant
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import com.adedom.myfood.utility.jwt.JwtHelper
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class AuthRepositoryImpl(
    private val jwtHelper: JwtHelper,
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {

    override fun login(username: String, password: String): Resource<BaseResponse<TokenResponse>> {
        val response = BaseResponse<TokenResponse>()

        val userId = authRemoteDataSource.findUserIdByUsernameAndPassword(
            username,
            encryptSHA(password),
            AppConstant.ACTIVE,
        )
        return if (userId != null) {
            response.status = ResponseKeyConstant.SUCCESS
            val tokenResponse = TokenResponse(
                accessToken = jwtHelper.encodeAccessToken(userId),
                refreshToken = jwtHelper.encodeRefreshToken(userId)
            )
            response.result = tokenResponse
            Resource.Success(response)
        } else {
            response.error = BaseError(message = "Username or password incorrect.")
            Resource.Error(response)
        }
    }

    override fun findUserByUsername(username: String): Long {
        return authRemoteDataSource.findUserByUsername(username)
    }

    override fun register(registerRequest: RegisterRequest): Resource<BaseResponse<TokenResponse>> {
        val response = BaseResponse<TokenResponse>()

        val insertUserCount = authRemoteDataSource.insertUser(
            userId = UUID.randomUUID().toString().replace("-", ""),
            registerRequest = registerRequest.copy(password = encryptSHA(registerRequest.password!!)),
            AppConstant.ACTIVE,
        ) ?: 0
        return if (insertUserCount > 0) {
            login(registerRequest.username!!, registerRequest.password)
        } else {
            response.error = BaseError(message = "Registration failed")
            Resource.Error(response)
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

    override fun findUserByUserIdAndPassword(userId: String, password: String): Long {
        return authRemoteDataSource.findUserByUserIdAndPassword(userId, encryptSHA(password))
    }

    override fun changePassword(userId: String, newPassword: String): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val isUpdateUserPassword = authRemoteDataSource.updateUserPassword(userId, encryptSHA(newPassword)) == 1
        return if (isUpdateUserPassword) {
            response.status = ResponseKeyConstant.SUCCESS
            response.result = "Change password success."
            Resource.Success(response)
        } else {
            response.error = BaseError(message = "Change password failed.")
            Resource.Error(response)
        }
    }
}