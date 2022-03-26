package com.adedom.myfood.data.repositories.profile

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.remote.profile.ProfileRemoteDataSource
import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.ChangeProfileRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.utility.constant.AppConstant
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class ProfileRepositoryImpl(
    private val profileRemoteDataSource: ProfileRemoteDataSource,
) : ProfileRepository {

    override fun getUserProfile(userId: String): UserEntity {
        return profileRemoteDataSource.getUserByUserId(userId)
    }

    override fun changeProfile(
        userId: String,
        changeProfileRequest: ChangeProfileRequest,
    ): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val isUpdateUserProfile = profileRemoteDataSource.updateUserProfile(userId, changeProfileRequest) == 1
        return if (isUpdateUserProfile) {
            response.status = ResponseKeyConstant.SUCCESS
            response.result = "Change profile success."
            Resource.Success(response)
        } else {
            response.error = BaseError(message = "Change profile failed.")
            Resource.Error(response)
        }
    }

    override fun deleteAccount(userId: String): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val isUpdateStatus = profileRemoteDataSource.updateUserStatus(userId, AppConstant.IN_ACTIVE) == 1
        return if (isUpdateStatus) {
            response.status = ResponseKeyConstant.SUCCESS
            response.result = "Delete account successfully."
            Resource.Success(response)
        } else {
            response.error = BaseError(message = "Delete account failed.")
            Resource.Error(response)
        }
    }
}