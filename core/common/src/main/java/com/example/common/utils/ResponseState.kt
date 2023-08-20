package com.example.common.utils

sealed interface ResponseState<out T> {
    data class Error(val exception: Exception) : ResponseState<Nothing>
    data class Success<R>(val data: R) : ResponseState<R>
}