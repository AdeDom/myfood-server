package com.adedom.myfood.data.resouce.local.auth

interface AuthLocalDataSource {

    suspend fun insertAuth(authId: String, accessToken: String, refreshToken: String, isBackup: Int): Int?
}