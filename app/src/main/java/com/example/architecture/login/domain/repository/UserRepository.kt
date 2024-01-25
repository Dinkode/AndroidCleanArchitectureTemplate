package com.example.architecture.login.domain.repository

import com.example.architecture.items.domain.utils.Resource
import com.example.architecture.login.data.apiServices.response.UserDataDto
import com.example.architecture.login.data.apiServices.request.LoginDto
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun login(loginDto: LoginDto): Flow<Resource<UserDataDto>>
}