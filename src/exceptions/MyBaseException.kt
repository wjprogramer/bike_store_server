package com.giant_giraffe.exceptions

abstract class MyBaseException(
    val errorCode: Int,
    val type: String? = null,
    message: String? = null,
    cause: Throwable? = null,
): Exception(message, cause)