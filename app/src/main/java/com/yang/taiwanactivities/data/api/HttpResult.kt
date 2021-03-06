package com.yang.taiwanactivities.data.api

sealed class HttpResult<out T : Any> {
    class Success<out T : Any>(val data: T) : HttpResult<T>()
    class Error(val message: String) : HttpResult<Nothing>()
}