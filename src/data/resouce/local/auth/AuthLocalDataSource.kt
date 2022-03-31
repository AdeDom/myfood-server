package com.adedom.myfood.data.resouce.local.auth

import com.adedom.myfood.data.models.entities.AuthEntity

interface AuthLocalDataSource {

    suspend fun replaceAuth(authId: String, accessToken: String, refreshToken: String, isBackup: Int): Int?

    suspend fun getAuthList(): List<AuthEntity>
}