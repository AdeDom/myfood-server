package com.adedom.myfood.data.repositories.profile

import com.adedom.myfood.data.resouce.remote.profile.ProfileRemoteDataSource
import com.adedom.myfood.utility.constant.AppConstant

class ProfileRepositoryImpl(
    private val profileRemoteDataSource: ProfileRemoteDataSource,
) : ProfileRepository {

    override fun updateUserStatusInActive(userId: String): Int {
        return profileRemoteDataSource.updateUserStatus(userId, AppConstant.IN_ACTIVE)
    }
}