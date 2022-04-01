package com.adedom.myfood.data.repositories.auth

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.entities.AuthMasterEntity
import com.adedom.myfood.data.models.request.RegisterRequest
import com.adedom.myfood.data.models.response.TokenResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.local.auth.AuthLocalDataSource
import com.adedom.myfood.data.resouce.remote.auth.AuthRemoteDataSource
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
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {

    override suspend fun login(username: String, password: String): Resource<BaseResponse<TokenResponse>> {
        val response = BaseResponse<TokenResponse>()

        val userId = authRemoteDataSource.findUserIdByUsernameAndPassword(
            username,
            encryptSHA(password),
            AppConstant.ACTIVE,
        )
        return if (userId != null) {
            val getAuthListOriginal = authLocalDataSource.getAuthListByStatusLoginOrRefresh()
            val getAuthList = getAuthListOriginal.map { authEntity ->
                AuthMasterEntity(
                    authId = authEntity.authId,
                    userId = jwtHelper.decodeJwtGetUserId(authEntity.accessToken),
                    status = authEntity.status,
                    isBackup = authEntity.isBackup,
                    created = authEntity.created,
                    updated = authEntity.updated,
                )
            }
            val getAuthIdList = getAuthList
                .filter { it.userId == userId }
                .map { it.authId }
            var updateAuthLogoutCount = 0
            getAuthIdList.forEach { authId ->
                updateAuthLogoutCount += authLocalDataSource.updateAuthStatusLogoutByAuthId(authId)
            }
            if (updateAuthLogoutCount == getAuthIdList.size) {
                val authId = UUID.randomUUID().toString().replace("-", "")
                val accessToken = jwtHelper.encodeAccessToken(userId)
                val refreshToken = jwtHelper.encodeRefreshToken(userId)
                val status = AppConstant.AUTH_LOGIN
                val isBackup = AppConstant.LOCAL_BACKUP
                val insertAuthCount = authLocalDataSource.insertAuth(authId, accessToken, refreshToken, status, isBackup)
                if (insertAuthCount == 1) {
                    val tokenResponse = TokenResponse(
                        accessToken = accessToken,
                        refreshToken = refreshToken,
                    )
                    response.status = ResponseKeyConstant.SUCCESS
                    response.result = tokenResponse
                    Resource.Success(response)
                } else {
                    response.error = BaseError(message = "Login invalid.")
                    Resource.Error(response)
                }
            } else {
                response.error = BaseError(message = "Login invalid.")
                Resource.Error(response)
            }
        } else {
            response.error = BaseError(message = "Username or password incorrect.")
            Resource.Error(response)
        }
    }

    override suspend fun findUserByUsername(username: String): Long {
        return authRemoteDataSource.findUserByUsername(username)
    }

    override suspend fun register(registerRequest: RegisterRequest): Resource<BaseResponse<TokenResponse>> {
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

    override suspend fun findUserByUserIdAndPassword(userId: String, password: String): Long {
        return authRemoteDataSource.findUserByUserIdAndPassword(userId, encryptSHA(password))
    }

    override suspend fun changePassword(userId: String, newPassword: String): Resource<BaseResponse<String>> {
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

    override suspend fun logout(): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        response.status = ResponseKeyConstant.SUCCESS
        response.result = "Logout success."
        return Resource.Success(response)
    }

    override suspend fun refreshToken(refreshTokenMaster: String): Resource<BaseResponse<TokenResponse>> {
        val response = BaseResponse<TokenResponse>()

        val userId = jwtHelper.decodeJwtGetUserId(refreshTokenMaster)

        val getAuthListOriginal = authLocalDataSource.getAuthListByStatusLoginOrRefresh()
        val getAuthList = getAuthListOriginal.map { authEntity ->
            AuthMasterEntity(
                authId = authEntity.authId,
                userId = jwtHelper.decodeJwtGetUserId(authEntity.accessToken),
                status = authEntity.status,
                isBackup = authEntity.isBackup,
                created = authEntity.created,
                updated = authEntity.updated,
            )
        }
        val getAuthIdList = getAuthList
            .filter { it.userId == userId }
            .map { it.authId }
        var updateAuthLogoutCount = 0
        getAuthIdList.forEach { authId ->
            updateAuthLogoutCount += authLocalDataSource.updateAuthStatusLogoutByAuthId(authId)
        }
        return if (updateAuthLogoutCount == getAuthIdList.size) {
            val authId = UUID.randomUUID().toString().replace("-", "")
            val accessToken = jwtHelper.encodeAccessToken(userId)
            val refreshToken = jwtHelper.encodeRefreshToken(userId)
            val status = AppConstant.AUTH_LOGIN
            val isBackup = AppConstant.LOCAL_BACKUP
            val insertAuthCount = authLocalDataSource.insertAuth(authId, accessToken, refreshToken, status, isBackup)
            if (insertAuthCount == 1) {
                val tokenResponse = TokenResponse(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                )
                response.status = ResponseKeyConstant.SUCCESS
                response.result = tokenResponse
                Resource.Success(response)
            } else {
                response.error = BaseError(message = "Refresh token invalid.")
                Resource.Error(response)
            }
        } else {
            response.error = BaseError(message = "Refresh token invalid.")
            Resource.Error(response)
        }
    }

    override suspend fun findTokenByAccessTokenAndRefreshToken(accessToken: String, refreshToken: String): Long {
        return authLocalDataSource.findTokenByAccessTokenAndRefreshToken(accessToken, refreshToken)
    }
}