package com.example.architecture.login.domain.models

data class UserModel(
    val firstName: String,
    val lastName: String,
    val token: String,
)