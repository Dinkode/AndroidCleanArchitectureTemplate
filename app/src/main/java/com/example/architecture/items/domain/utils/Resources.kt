package com.example.architecture.items.domain.utils

sealed class Resource<out T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
    object Loading: Resource<Nothing>()
}

interface ToModel<out T> {
    fun toModel():T
}
