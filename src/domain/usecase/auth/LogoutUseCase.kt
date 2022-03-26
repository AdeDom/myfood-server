package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class LogoutUseCase {

    operator fun invoke(): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        return when {
            else -> {
                response.status = ResponseKeyConstant.SUCCESS
                response.result = "Logout success."
                Resource.Success(response)
            }
        }
    }
}