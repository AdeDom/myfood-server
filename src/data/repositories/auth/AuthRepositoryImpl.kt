package com.adedom.myfood.data.repositories.auth

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.remote.auth.AuthRemoteDataSource
import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.LoginRequest
import com.adedom.myfood.route.models.request.RegisterRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.utility.constant.AppConstant
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {

    override fun findUserByUsernameAndPassword(loginRequest: LoginRequest): UserEntity? {
        return authRemoteDataSource.findUserByUsernameAndPassword(
            loginRequest = loginRequest.copy(password = encryptSHA(loginRequest.password!!)),
            AppConstant.ACTIVE,
        )
    }

    override fun findUserByUsername(username: String): Long {
        return authRemoteDataSource.findUserByUsername(username)
    }

    override fun insertUser(registerRequest: RegisterRequest): Int? {
        return authRemoteDataSource.insertUser(
            userId = UUID.randomUUID().toString().replace("-", ""),
            registerRequest = registerRequest.copy(password = encryptSHA(registerRequest.password!!)),
            AppConstant.ACTIVE,
        )
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

    override fun updateUserPassword(userId: String, newPassword: String): Resource<BaseResponse<String>> {
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