package com.adedom.myfood.data.resouce.local.auth

import com.adedom.myfood.data.models.entities.AuthEntity

interface AuthLocalDataSource {

    suspend fun insertAuth(
        authId: String,
        accessToken: String,
        refreshToken: String,
        status: String,
        isBackup: Int,
    ): Int?

    suspend fun getAuthListByStatusLoginOrRefresh(): List<AuthEntity>

    suspend fun updateAuthStatusLogoutByAuthId(authId: String): Int
}