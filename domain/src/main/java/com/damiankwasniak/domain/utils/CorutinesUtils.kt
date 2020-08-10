package com.damiankwasniak.domain.utils

sealed class AsyncResult<out R> {

    data class Success<out T>(val data: T) : AsyncResult<T>()

    data class Error(val exception: Exception) : AsyncResult<Nothing>()
}
