package com.example.coop.ui.logincomponents.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coop.iteractor.LoginRepo
import com.example.coop.models.LoginResponse
import com.example.coop.models.LoginUser
import com.example.coop.ui.logincomponents.state.LoginState
import com.example.coop.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepo,

    ) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onChangeUsername(text: String) {
        _uiState.update {
            it.copy(username = text)
        }
    }

    fun onChangePassword(text: String) {
        _uiState.update {
            it.copy(password = text)
        }
    }


    fun onChangePasswordVisibility() {
        _uiState.update {
            it.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
        }
    }

    fun onChangeLoadingState() {
        _uiState.update {
            it.copy(isLoading = !_uiState.value.isLoading)
        }
    }

    fun loginUser(onSuccessLogin: (LoginResponse) -> Unit) {
        viewModelScope.launch {
            if (_uiState.value.username.isEmpty()){
                _eventFlow.emit("Please fill in your email")
                return@launch
            }
            if (_uiState.value.password.isEmpty()){
                _eventFlow.emit("Please fill in your password")
                return@launch
            }
            onChangeLoadingState()
            val response = repository.loginUser(
                LoginUser(
                    username = _uiState.value.username,
                    password = _uiState.value.password
                )
            )
            when (response) {
                is NetworkResult.Success -> {
                    onChangeLoadingState()
                    onSuccessLogin(response.data)
                }
                is NetworkResult.Error -> {
                    onChangeLoadingState()
                    _eventFlow.emit("An unexpected error occurred")
                }
                is NetworkResult.Exception -> {
                    onChangeLoadingState()
                    _eventFlow.emit("An unexpected exception occurred")
                }

            }

        }
    }
}