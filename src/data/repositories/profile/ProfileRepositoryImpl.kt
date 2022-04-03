package com.adedom.myfood.data.repositories.profile

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.entities.AuthMasterEntity
import com.adedom.myfood.data.models.entities.UserEntity
import com.adedom.myfood.data.models.request.ChangeProfileRequest
import com.adedom.myfood.data.models.response.UserProfileResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.local.auth.AuthLocalDataSource
import com.adedom.myfood.data.resouce.local.user.UserLocalDataSource
import com.adedom.myfood.data.resouce.remote.profile.ProfileRemoteDataSource
import com.adedom.myfood.data.resouce.remote.random_user.RandomUserRemoteDataSource
import com.adedom.myfood.data.resouce.remote.user.UserRemoteDataSource
import com.adedom.myfood.utility.constant.AppConstant
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import com.adedom.myfood.utility.jwt.JwtHelper

class ProfileRepositoryImpl(
    private val jwtHelper: JwtHelper,
    private val userLocalDataSource: UserLocalDataSource,
    private val authLocalDataSource: AuthLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val profileRemoteDataSource: ProfileRemoteDataSource,
    private val randomUserRemoteDataSource: RandomUserRemoteDataSource,
) : ProfileRepository {

    override suspend fun userProfile(userId: String): Resource<BaseResponse<UserProfileResponse>> {
        val response = BaseResponse<UserProfileResponse>()

        val userLocalAll = userLocalDataSource.getUserAll()
        if (userLocalAll.isEmpty()) {
            val userRemoteAll = userRemoteDataSource.getUserAll()
            userLocalDataSource.deleteUserAll()
            val handleImageProfileDefaultList = handleImageProfileDefaultList(userRemoteAll)
            val listLocalList = userLocalDataSource.insertUserAll(handleImageProfileDefaultList)
            if (listLocalList != handleImageProfileDefaultList.size) {
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
                image = userEntity.image,
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

    private suspend fun handleImageProfileDefaultList(userList: List<UserEntity>): List<UserEntity> {
        val image = try {
            val getRandomUser = randomUserRemoteDataSource.getRandomUser()
            if (getRandomUser.results.isEmpty()) {
                "https://blog.jetbrains.com/wp-content/uploads/2018/11/kotlin-Ktor.png"
            } else {
                getRandomUser.results[0].picture?.large
            }
        } catch (e: Throwable) {
            "https://blog.jetbrains.com/wp-content/uploads/2018/11/kotlin-Ktor.png"
        }

        return userList.map { userEntity ->
            UserEntity(
                userId = userEntity.userId,
                username = userEntity.username,
                password = userEntity.password,
                name = userEntity.name,
                email = userEntity.email,
                mobileNo = userEntity.mobileNo,
                address = userEntity.address,
                image = userEntity.image ?: image,
                status = userEntity.status,
                created = userEntity.created,
                updated = userEntity.updated,
            )
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
            val getAuthListOriginal = authLocalDataSource.getAuthListByStatusLoginOrRefresh()
            val getAuthList = getAuthListOriginal.map { authEntity ->
                AuthMasterEntity(
                    authId = authEntity.authId,
                    userId = jwtHelper.decodeJwtGetUserId(authEntity.accessToken),
                    status = authEntity.status,
                    isBackup = authEntity.isBackup,
                    created = authEntity.created,
                    updated = authEntity.updated,
                )
            }
            val getAuthIdList = getAuthList
                .filter { it.userId == userId }
                .map { it.authId }
            var updateAuthLogoutCount = 0
            getAuthIdList.forEach { authId ->
                updateAuthLogoutCount += authLocalDataSource.updateAuthStatusLogoutByAuthId(authId)
            }
            if (updateAuthLogoutCount == getAuthIdList.size) {
                response.status = ResponseKeyConstant.SUCCESS
                response.result = "Delete account successfully."
                Resource.Success(response)
            } else {
                response.error = BaseError(message = "Delete account failed.")
                Resource.Error(response)
            }
        } else {
            response.error = BaseError(message = "Delete account failed.")
            Resource.Error(response)
        }
    }
}