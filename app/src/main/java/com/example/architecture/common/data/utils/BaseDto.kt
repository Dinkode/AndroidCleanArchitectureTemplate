package com.example.architecture.common.data.utils

interface BaseDto<T> {
    fun toModel(): T
}