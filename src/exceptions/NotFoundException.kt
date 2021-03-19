package com.giant_giraffe.exceptions

class NotFoundException(
    message: String? = null,
): MyBaseException(
    errorCode = 4040
)