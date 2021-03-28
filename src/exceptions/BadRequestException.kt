package com.giant_giraffe.exceptions

class BadRequestException (
    message: String? = null,
): MyBaseException(
    errorCode = 4000,
    message = message,
)