package com.adedom.myfood.data.repositories.profile

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.remote.profile.ProfileRemoteDataSource
import com.adedom.myfood.route.models.base.BaseError
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.request.ChangeProfileRequest
import com.adedom.myfood.route.models.response.UserProfileResponse
import com.adedom.myfood.utility.constant.AppConstant
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import org.joda.time.DateTime

class ProfileRepositoryImpl(
    private val profileRemoteDataSource: ProfileRemoteDataSource,
) : ProfileRepository {

    override fun userProfile(userId: String): Resource<BaseResponse<UserProfileResponse>> {
        val response = BaseResponse<UserProfileResponse>()

        val userEntity = profileRemoteDataSource.getUserByUserId(userId)
        return if (userEntity != null) {
            val userProfileResponse = UserProfileResponse(
                userId = userEntity.userId,
                username = userEntity.username,
                name = userEntity.name,
                email = userEntity.email,
                mobileNo = userEntity.mobileNo,
                address = userEntity.address,
                status = userEntity.status,
                created = toDateTimeString(userEntity.created).orEmpty(),
                updated = toDateTimeString(userEntity.updated),
            )
            response.status = ResponseKeyConstant.SUCCESS
            response.result = userProfileResponse
            Resource.Success(response)
        } else {
            response.error = BaseError(message = "User profile is null or empty.")
            Resource.Error(response)
        }
    }

    private fun toDateTimeString(dateTime: DateTime?): String? {
        return dateTime?.toString("d/M/yyyy H:m")
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