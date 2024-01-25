package com.example.architecture.login.data.apiServices

import com.example.architecture.login.data.apiServices.response.UserDataDto
import com.example.architecture.login.data.apiServices.request.LoginDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {

    @POST("auth/login")
    suspend fun login(@Body login: LoginDto
    ): Response<UserDataDto>

    @GET("refreshToken")
    suspend fun refreshToken(@Body refreshToken: String
    ): Response<UserDataDto>
}