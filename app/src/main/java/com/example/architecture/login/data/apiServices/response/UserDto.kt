package com.example.architecture.login.data.apiServices.response

import com.example.architecture.common.data.utils.BaseDto
import com.example.architecture.login.domain.models.UserModel


data class UserDataDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val token: String

) : BaseDto<UserModel> {
    override fun toModel(): UserModel {
        return UserModel(firstName, lastName, token)
    }

}