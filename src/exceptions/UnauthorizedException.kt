package com.giant_giraffe.exceptions

class UnauthorizedException(
    message: String? = null,
): MyBaseException(
    errorCode = 4010,
    message = message,
)