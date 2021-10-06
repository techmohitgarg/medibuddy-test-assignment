package com.example.meddybuddyassigment.errorProvider

interface ErrorProvider {
    fun getErrorMessage(error: Throwable?): String

    fun isNetworkError(error: Throwable?): Boolean
}