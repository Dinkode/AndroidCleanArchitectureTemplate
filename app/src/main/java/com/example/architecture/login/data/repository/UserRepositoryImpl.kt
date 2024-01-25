package com.example.architecture.login.data.repository

import com.example.architecture.login.data.apiServices.LoginService
import com.example.architecture.login.data.apiServices.response.UserDataDto
import com.example.architecture.login.data.apiServices.request.LoginDto
import com.example.architecture.login.domain.repository.UserRepository
import javax.inject.Inject
import com.example.architecture.common.data.utils.apiFlow
import com.example.architecture.items.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl @Inject constructor(
    private val api: LoginService
): UserRepository {


    override fun login(loginDto: LoginDto): Flow<Resource<UserDataDto>> = apiFlow {
        api.login(loginDto)
    }
}