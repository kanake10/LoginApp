package com.example.coop.utils

import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

suspend fun <T : Any> safeApiCall(
    execute: suspend () -> Response<T>
): NetworkResult<T> {
    val TAG = "Network Error"
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            NetworkResult.Error(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        Timber.tag(TAG).i("The HTTP error causing this is -----> $e")
        NetworkResult.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        Timber.tag(TAG).i("The HTTP error causing this is -----> $e")
        NetworkResult.Exception(e)
    }
}

sealed class NetworkResult<T : Any> {
    class Success<T: Any>(val data: T) : NetworkResult<T>()
    class Error<T: Any>(val code: Int, val message: String?) : NetworkResult<T>()
    class Exception<T: Any>(val e: Throwable) : NetworkResult<T>()
}