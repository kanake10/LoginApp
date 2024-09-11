package com.example.coop.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginUser(
    val username:String,
    val password:String,
)
