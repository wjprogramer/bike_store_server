package com.giant_giraffe.exceptions

class ForbiddenException(
    message: String? = null,
): MyBaseException(
    errorCode = 4030,
    message = message,
)