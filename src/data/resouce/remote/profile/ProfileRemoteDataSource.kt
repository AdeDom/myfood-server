package com.adedom.myfood.data.resouce.remote.profile

interface ProfileRemoteDataSource {

    fun updateUserStatus(userId: String, status: String): Int
}