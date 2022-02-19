package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.domain.usecase.Resource
import com.adedom.myfood.route.models.response.base.BaseResponse
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