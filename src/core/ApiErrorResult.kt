package com.giant_giraffe.core

import io.ktor.application.*
import io.ktor.response.*

// ref:
// https://nordicapis.com/best-practices-api-error-handling/

suspend inline fun ApplicationCall.respondApiErrorResult(
    code: Int,
    type: String?,
    message: String?,
    traceId: String? = null,
) {
    respond(
        ApiErrorResult(
            code,
            type,
            message,
            traceId,
        )
    )
}

data class ApiErrorResult(
    val code: Int,
    val type: String?,
    val message: String?,
    val traceId: String? = null,
)