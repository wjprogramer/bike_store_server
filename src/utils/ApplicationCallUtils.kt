package com.giant_giraffe.utils

import com.giant_giraffe.data.common.User
import io.ktor.application.*
import io.ktor.auth.*
import java.lang.Exception

fun ApplicationCall.getUser(): User {
    try {
        val principal = authentication.principal

        if (principal is User) {
            return principal
        } else {
            throw Exception()
        }
    } catch (e: Exception) {
        throw e
    }
}
