package com.adedom.myfood.data.repositories.auth

import com.adedom.myfood.data.resouce.remote.auth.AuthRemoteDataSource
import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.LoginRequest
import com.adedom.myfood.route.models.request.RegisterRequest
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class AuthRepositoryImpl(
    private val dataSource: AuthRemoteDataSource,
) : AuthRepository {

    override fun findUserByUsernameAndPassword(loginRequest: LoginRequest): UserEntity? {
        return dataSource.findUserByUsernameAndPassword(
            loginRequest = loginRequest.copy(password = encryptSHA(loginRequest.password!!)),
        )
    }

    override fun findUserByUsername(username: String): Long {
        return dataSource.findUserByUsername(username)
    }

    override fun insertUser(registerRequest: RegisterRequest): Int? {
        return dataSource.insertUser(
            userId = UUID.randomUUID().toString().replace("-", ""),
            registerRequest = registerRequest.copy(password = encryptSHA(registerRequest.password!!)),
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
}