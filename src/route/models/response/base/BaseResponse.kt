package com.adedom.myfood.route.models.response.base

import com.adedom.myfood.utility.constant.ResponseKeyConstant

@kotlinx.serialization.Serializable
data class BaseResponse<T>(
    var version: String = ResponseKeyConstant.VERSION,
    var status: String = ResponseKeyConstant.ERROR,
    var result: T? = null,
    var error: BaseError? = null,
)