package com.giant_giraffe.exceptions

class UnknownException(
    message: String? = "Internal Server Error"
): MyBaseException(
    errorCode = 5000,
    message = message
)