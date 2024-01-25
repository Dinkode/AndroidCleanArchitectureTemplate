package com.example.architecture.login.domain.use_cases

import com.example.architecture.items.domain.utils.Resource
import com.example.architecture.login.data.apiServices.request.LoginDto
import com.example.architecture.login.data.apiServices.response.UserDataDto
import com.example.architecture.login.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import java.lang.Error
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(username: String, password: String): Flow<Resource<UserDataDto>> {

        if (username.isBlank()) {
            throw Error("Username should not be empty")
        }

        if (password.isBlank()) {
            throw Error("Password should not be empty")
        }


        return repository.login(LoginDto(username, password))
    }
}