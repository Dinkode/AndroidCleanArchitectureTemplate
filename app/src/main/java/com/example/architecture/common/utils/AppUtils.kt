package com.example.architecture.common.utils

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
}