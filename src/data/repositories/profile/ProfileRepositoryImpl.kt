package com.adedom.myfood.data.repositories.profile

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.local.user.UserLocalDataSource
import com.adedom.myfood.data.resouce.remote.profile.ProfileRemoteDataSource
import com.adedom.myfood.data.resouce.remote.user.UserRemoteDataSource
import com.adedom.myfood.route.models.base.BaseError
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.request.ChangeProfileRequest
import com.adedom.myfood.route.models.response.UserProfileResponse
import com.adedom.myfood.utility.constant.AppConstant
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class ProfileRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val profileRemoteDataSource: ProfileRemoteDataSource,
) : ProfileRepository {

    override suspend fun userProfile(userId: String): Resource<BaseResponse<UserProfileResponse>> {
        val response = BaseResponse<UserProfileResponse>()

        val userLocalAll = userLocalDataSource.getUserAll()
        if (userLocalAll.isEmpty()) {
            val userRemoteAll = userRemoteDataSource.getUserAll()
            userLocalDataSource.deleteUserAll()
            val listLocalList = userLocalDataSource.insertUserAll(userRemoteAll)
            if (listLocalList != userRemoteAll.size) {
                userLocalDataSource.deleteUserAll()
            }
        }

        val userEntity = userLocalDataSource.getUserByUserId(userId)
        return if (userEntity != null) {
            val userProfileResponse = UserProfileResponse(
                userId = userEntity.userId,
                username = userEntity.username,
                name = userEntity.name,
                email = userEntity.email,
                mobileNo = userEntity.mobileNo,
                address = userEntity.address,
                status = userEntity.status,
                created = userEntity.created.toString(AppConstant.DATE_TIME_FORMAT_RESPONSE),
                updated = userEntity.updated?.toString(AppConstant.DATE_TIME_FORMAT_RESPONSE),
            )
            response.status = ResponseKeyConstant.SUCCESS
            response.result = userProfileResponse
            Resource.Success(response)
        } else {
            response.error = BaseError(message = "User profile is null or empty.")
            Resource.Error(response)
        }
    }

    override suspend fun changeProfile(
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

    override suspend fun deleteAccount(userId: String): Resource<BaseResponse<String>> {
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