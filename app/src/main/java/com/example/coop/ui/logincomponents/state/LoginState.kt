package com.example.coop.ui.logincomponents.state

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
)
