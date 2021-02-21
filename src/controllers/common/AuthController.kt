package com.giant_giraffe.controllers.common

import com.giant_giraffe.data.common.EmailPasswordCredential
import com.giant_giraffe.enums.UserType
import com.giant_giraffe.services.common.auth.AuthService
import com.giant_giraffe.services.common.user.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Route.authController() {

    val userService by di().instance<UserService>()
    val authService by di().instance<AuthService>()

    post("/customerLogin") {
        val credentials = call.receive<EmailPasswordCredential>()

        val user = userService.findUserByCredentials(credentials, UserType.CUSTOMER)
        val token = authService.makeToken(user)

        call.respondText(token)
    }

    post("/staffLogin") {
        val credentials = call.receive<EmailPasswordCredential>()

        val user = userService.findUserByCredentials(credentials, UserType.STAFF)
        val token = authService.makeToken(user)

        call.respondText(token)
    }

}

