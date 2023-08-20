package com.example.common.utils

suspend fun <T> getResponseState(request: suspend () -> T): ResponseState<T> {
    return try {
        ResponseState.Success(request())
    } catch (e: Exception) {
        ResponseState.Error(e)
    }
}
