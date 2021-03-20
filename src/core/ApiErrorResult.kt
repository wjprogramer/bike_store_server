package com.giant_giraffe.core

import com.giant_giraffe.exceptions.MyBaseException
import io.ktor.application.*
import io.ktor.response.*

// ref:
// https://nordicapis.com/best-practices-api-error-handling/

suspend inline fun ApplicationCall.respondApiErrorResult(
    exception: MyBaseException
) {
    respond(
        ApiErrorResult(
            code = exception.errorCode,
            type = exception.type,
            message = exception.message,
        )
    )
}

suspend inline fun ApplicationCall.respondApiErrorResult(
    code: Int,
    type: String?,
    message: String?,
    traceId: String? = null,
) {
    respond(
        ApiErrorResult(
            code = code,
            type = type,
            message = message,
            traceId = traceId,
        )
    )
}

/**
 * Example:
 *
 * ```json
 * {
 *      "title": "Insert Failed (Some Error)",
 *      "message": "Please enter fill fields correctly (Error details)",
 *      "code": 10,
 *      "errors": [
 *          {
 *              "field": "email",
 *              "message": "Invalid email address."
 *          }
 *      ]
 * }
 * ```
 */
data class ApiErrorResult(
    // for view
    val title: String? = null,
    val message: String?,
    val code: Int,
    // for trace
    val type: String?,
    val errors: List<ErrorItem>? = null,
    val traceId: String? = null,
)

data class ErrorItem(
    val field: String? = "",
    val message: String? = "",
)