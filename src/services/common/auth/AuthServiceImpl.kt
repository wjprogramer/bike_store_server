package com.giant_giraffe.services.common.auth

import com.giant_giraffe.app.JwtConfig
import com.giant_giraffe.data.common.User
import io.ktor.application.*
import org.kodein.di.instance
import org.kodein.di.ktor.di

class AuthServiceImpl (application: Application): AuthService {

    private val jwtConfig by application.di().instance<JwtConfig>()

    override fun makeToken(user: User): String {
        return jwtConfig.makeToken(user)
    }

}