package com.giant_giraffe.core

import io.ktor.application.*
import io.ktor.response.*

const val SUCCESS_CODE = 2000

suspend inline fun <T> ApplicationCall.respondApiResult(
    actionName: String? = null,
    message: String? = null,
    result: T,
) {
    respond(
        ApiResult(
            actionName = actionName,
            message = message,
            result = result,
        )
    )
}

data class ApiResult<T>(
    val code: Int = SUCCESS_CODE,
    val actionName: String? = null,
    val message: String? = null,
    val result: T,
)