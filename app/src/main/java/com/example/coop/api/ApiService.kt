package com.example.coop.api

import com.example.coop.models.LoginResponse
import com.example.coop.models.LoginUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/auth/login")
    suspend fun loginUser(@Body loginUser: LoginUser): Response<LoginResponse>
}