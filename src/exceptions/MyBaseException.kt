package com.giant_giraffe.exceptions

abstract class MyBaseException(
    message: String? = null,
    cause: Throwable? = null,
    val errorCode: Int,
): Exception(message, cause)