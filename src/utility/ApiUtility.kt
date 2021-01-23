package com.giant_giraffe.utility

import com.giant_giraffe.core.respondApiErrorResult
import com.giant_giraffe.exceptions.MyBaseException
import com.giant_giraffe.exceptions.UnknownException
import io.ktor.application.*
import java.lang.Exception

object ApiUtility {

    suspend fun handleError(e: Exception, call: ApplicationCall) {
        e.printStackTrace()
        val error = if (e !is MyBaseException) {
            UnknownException()
        } else {
            e
        }
        call.respondApiErrorResult(
            code = error.errorCode,
            type = error.javaClass.simpleName,
            message = error.message,
        )
    }

}