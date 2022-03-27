package com.adedom.myfood.domain.usecase.profile

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.profile.ProfileRepository
import com.adedom.myfood.route.models.base.BaseError
import com.adedom.myfood.route.models.base.BaseResponse

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