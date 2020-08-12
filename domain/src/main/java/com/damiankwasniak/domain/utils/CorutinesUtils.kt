package com.damiankwasniak.domain.utils

sealed class AsyncResult<out R> {

    data class Success<out T>(val data: T) : AsyncResult<T>()

    data class Error(val exception: Exception) : AsyncResult<Nothing>()
}

fun <T, R> AsyncResult<T>.map(mapFunction: (data: T) -> R): AsyncResult<R> {
    return when (this) {
        is AsyncResult.Success -> AsyncResult.Success(mapFunction(this.data))
        is AsyncResult.Error -> this
    }
}
