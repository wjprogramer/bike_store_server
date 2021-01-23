package com.giant_giraffe.core

import io.ktor.application.*
import io.ktor.response.*

suspend inline fun <T> ApplicationCall.respondApiResult(
    actionName: String? = null,
    message: String? = null,
    result: T,
) {
    respond(
        ApiResult(
            actionName,
            message,
            result,
        )
    )
}

data class ApiResult<T>(
    val actionName: String? = null,
    val message: String? = null,
    val result: T,
)