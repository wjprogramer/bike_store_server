package com.giant_giraffe.utils

import com.giant_giraffe.data.common.User
import io.ktor.application.*
import io.ktor.auth.*

val ApplicationCall.user get() = authentication.principal<User>()
