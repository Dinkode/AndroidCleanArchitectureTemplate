package com.example.architecture.login.data.mappers

import com.example.architecture.login.data.apiServices.response.UserDataDto
import com.example.architecture.login.domain.models.UserModel

fun UserDataDto.toUserModelMap(): UserModel {
        return UserModel(firstName, lastName, token)
}