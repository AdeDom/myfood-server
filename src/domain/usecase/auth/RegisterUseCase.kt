package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.repositories.auth.AuthRepository
import com.adedom.myfood.domain.usecase.Resource
import com.adedom.myfood.route.models.request.RegisterRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class RegisterUseCase(
    private val authRepository: AuthRepository,
) {

    operator fun invoke(registerRequest: RegisterRequest): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val (username, password, name) = registerRequest
        return when {
            username.isNullOrBlank() -> {
                response.error = BaseError(code = "001", message = "Username is null or blank.")
                Resource.Error(response)
            }
            password.isNullOrBlank() -> {
                response.error = BaseError(code = "002", message = "Password is null or blank.")
                Resource.Error(response)
            }
            name.isNullOrBlank() -> {
                response.error = BaseError(code = "003", message = "Name is null or blank.")
                Resource.Error(response)
            }
            else -> {
                val usernameCount = authRepository.findUserByUsername(username)
                if (usernameCount == 0L) {
                    val isSuccess = authRepository.insertUser(registerRequest) ?: 0
                    if (isSuccess > 0) {
                        response.status = ResponseKeyConstant.SUCCESS
                        response.result = "Register successfully"
                        Resource.Success(response)
                    } else {
                        response.error = BaseError(code = "005", message = "Registration failed")
                        Resource.Error(response)
                    }
                } else {
                    response.error = BaseError(code = "004", message = "This username already exists.")
                    Resource.Error(response)
                }
            }
        }
    }
}