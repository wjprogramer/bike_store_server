package com.giant_giraffe.app

import com.giant_giraffe.data.common.User
import com.giant_giraffe.enums.toUserType
import com.giant_giraffe.services.common.user.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Application.initAuth() {

    val jwtConfig by di().instance<JwtConfig>()

    install(Authentication) {
        jwt {
            verifier(jwtConfig.verifier)
            realm = "ktor.io"
            validate { credential ->
                if (!credential.payload.audience.contains(jwtConfig.audience)) {
                    return@validate null
                }

                User(
                    id = credential.payload.getClaim("id").asInt(),
                    email = credential.payload.getClaim("email").asString(),
                    type = credential.payload.getClaim("type").asString().toUserType(),
                )
            }
        }
    }
}