package com.giant_giraffe.exceptions

class BadRequestException (
    message: String? = null,
): MyBaseException(
    errorCode = 4030,
    message = message,
)