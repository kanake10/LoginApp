package com.example.coop.iteractor

import com.example.coop.api.ApiService
import com.example.coop.models.LoginResponse
import com.example.coop.models.LoginUser
import com.example.coop.utils.NetworkResult
import com.example.coop.utils.safeApiCall
import javax.inject.Inject

class LoginRepoImpl @Inject constructor(private val apiService: ApiService): LoginRepo {
    override suspend fun loginUser(loginUser: LoginUser): NetworkResult<LoginResponse> {
        return safeApiCall { apiService.loginUser(loginUser) }
    }
}