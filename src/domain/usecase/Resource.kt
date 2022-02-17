package com.adedom.myfood.domain.usecase

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error<out T : Any>(val error: T) : Resource<T>()
}