package com.example.kasirtoko.utils

import com.example.kasirtoko.vo.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

suspend inline fun <T> safeApiCall(crossinline action: suspend () -> ResultWrapper<T>): ResultWrapper<T> {
    return withContext(Dispatchers.IO) {
        try {
            action()
        } catch (e: Exception) {
            Timber.e(e)
            ResultWrapper.Error(message = if (e.message.isNullOrEmpty()) "An unknown error occured" else e.message.toString())
        }
    }
}