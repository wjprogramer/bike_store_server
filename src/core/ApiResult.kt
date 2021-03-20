package com.giant_giraffe.core

import com.giant_giraffe.data.BaseModel
import com.giant_giraffe.exceptions.UnknownException
import com.giant_giraffe.utils.printError
import io.ktor.application.*
import io.ktor.response.*

const val SUCCESS_CODE = 2000

suspend inline fun <T> ApplicationCall.respondApiResult(
    result: T,
    code: Int = SUCCESS_CODE,
    title: String? = null,
    message: String? = null,
) {
    val resultContainsModel = result is BaseModel<*> || (
            result is PagedData<*> &&
                    result.data.firstOrNull { it is BaseModel<*> } != null
            )

    if (resultContainsModel) {
        printError("Shouldn't return model")
        throw UnknownException()
    }

    respond(
        ApiResult(
            result = result,
            code = code,
            title = title,
            message = message,
        )
    )
}

data class ApiResult<T>(
    val result: T,
    val code: Int = SUCCESS_CODE,
    val title: String? = null,
    val message: String? = null,
)
