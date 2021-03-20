package com.giant_giraffe.app

import com.auth0.jwt.exceptions.JWTVerificationException
import com.giant_giraffe.core.respondApiErrorResult
import com.giant_giraffe.data.common.User
import com.giant_giraffe.enums.toUserType
import com.giant_giraffe.exceptions.UnauthorizedException
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
            challenge { defaultScheme, realm ->
                println("challenge: $defaultScheme, $realm")
                val authHeader = call.request.headers["Authorization"]

                val errorMessage = when {
                    authHeader == null -> {
                        "No authorization header"
                    }
                    authHeader.isEmpty() -> {
                        "Authorization token empty"
                    }
                    else -> {
                        try {
                            val jwt = authHeader.replace("Bearer ", "")
                            jwtConfig.verifier.verify(jwt)
                            ""
                        } catch (e: Exception) {
                            if (e is JWTVerificationException) {
                                if (e.localizedMessage.contains("expired"))
                                    "Token expired"
                                else
                                    "Invalid token"
                            } else {
                                "Unknown token error"
                            }
                        }
                    }
                }

                // if error throw UnauthorizedException
                if (errorMessage.isNotEmpty()) {
                    throw UnauthorizedException(
                        message = errorMessage
                    )
                }
            }
            // TODO: Just some sample code from internet for refer
//            validate {
//                if (it.payload.audience.contains(AUDIENCE)) {
//                    it.payload.getClaim("id").asString().let { id -> userDao.getUserById(id) }
//                } else null
//            }
        }
    }
}