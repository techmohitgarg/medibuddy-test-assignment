package com.example.meddybuddyassigment.errorProvider

import android.content.Context
import com.example.meddybuddyassigment.R
import org.json.JSONObject
import retrofit2.HttpException
import java.net.*

class ErrorProviderImpl(val context: Context) : ErrorProvider {

    override fun getErrorMessage(error: Throwable?): String {
        var errorJsonString = ""

        try {
            when {
                isNetworkError(error) -> {
                    errorJsonString = context.getString(R.string.oops_no_internet_connection)
                }
                error is HttpException -> {
                    errorJsonString =
                        JSONObject(error.response()?.errorBody()?.string()).getString("message")
                }
            }
        } catch (ex: Exception) {
            return when (error) {
                is SocketTimeoutException,
                is UnknownHostException -> {
                    context.getString(R.string.oops_no_internet_connection)
                }
                else -> {
                    context.getString(R.string.something_is_not_right_here)
                }
            }
        }

        if (errorJsonString.isEmpty()) return context.getString(R.string.something_is_not_right_here)

        return errorJsonString
    }

    /**
     * Is the exception a network error
     * Checks type against a certain set of exception
     */
    override fun isNetworkError(error: Throwable?): Boolean {
        error?.let {
            return when (it) {
                is SocketException,
                is UnknownHostException,
                is SocketTimeoutException,
                is BindException,
                is ConnectException,
                is NoRouteToHostException -> true

                else -> false
            }
        }

        return false
    }
}