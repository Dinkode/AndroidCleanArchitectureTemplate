package com.example.architecture.common.utils

data class BaseRequestState<T> (
    val data: T?,
    val isLoading: Boolean,
    val error: String?
)