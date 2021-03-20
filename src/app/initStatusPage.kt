package com.giant_giraffe.app

import com.giant_giraffe.core.respondApiErrorResult
import com.giant_giraffe.exceptions.MyBaseException
import com.giant_giraffe.exceptions.UnknownException
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import java.lang.Exception

fun Application.initStatusPage() {
    install(StatusPages) {
        // others
        exception<MyBaseException> { cause ->
            ApiUtility.handleError(cause, call)
        }
        // unknown
        exception<Exception> { cause ->
            cause.printStackTrace()
            call.respondApiErrorResult(UnknownException())
        }
    }
}