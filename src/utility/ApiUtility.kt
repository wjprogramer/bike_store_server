package com.giant_giraffe.utility

import com.giant_giraffe.core.respondApiErrorResult
import com.giant_giraffe.exceptions.MyBaseException
import com.giant_giraffe.exceptions.UnknownException
import io.ktor.application.*
import java.lang.Exception

object ApiUtility {

    // TODO: Need to process built-in exception
    suspend fun handleError(e: Exception, call: ApplicationCall) {
        val error = if (e !is MyBaseException) {
            e.printStackTrace()
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