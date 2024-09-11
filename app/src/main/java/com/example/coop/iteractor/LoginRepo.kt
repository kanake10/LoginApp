package com.example.coop.iteractor

import com.example.coop.models.LoginResponse
import com.example.coop.models.LoginUser
import com.example.coop.utils.NetworkResult

interface LoginRepo {
    suspend fun loginUser(loginUser: LoginUser): NetworkResult<LoginResponse>
}