package com.adedom.myfood.data.repositories.profile

interface ProfileRepository {

    fun updateUserStatusInActive(userId: String): Int
}