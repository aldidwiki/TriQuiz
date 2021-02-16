package com.aldidwiki.myquizapp.data.source.remote

sealed class ApiResponse<out R> {
    data class Success<out T>(val body: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}
