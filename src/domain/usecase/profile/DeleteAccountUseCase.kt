package com.adedom.myfood.domain.usecase.profile

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.profile.ProfileRepository

class DeleteAccountUseCase(
    private val profileRepository: ProfileRepository,
) {

    suspend operator fun invoke(userId: String): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        return when {
            userId.isBlank() -> {
                response.error = BaseError(message = "User id is blank.")
                Resource.Error(response)
            }
            else -> {
                profileRepository.deleteAccount(userId)
            }
        }
    }
}