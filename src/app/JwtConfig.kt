package com.giant_giraffe.app

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import com.giant_giraffe.data.common.User
import io.ktor.application.*
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.util.*

const val JWT_CONFIG_KEY = "ktor.jwt-config"

class JwtConfig(private val environment: ApplicationEnvironment) {

    private val issuer: String
    private val secret: String
    val audience: String
    private val algorithm: Algorithm

    private val validityInMs = 36_000_00 * 10 // 10 hours

    val  verifier: JWTVerifier

    init {
        val props = getProps()

        issuer = props.getProperty("issuer")
        secret = props.getProperty("secret")
        audience = props.getProperty("audience")
        algorithm = Algorithm.HMAC512(secret)

        verifier = JWT
            .require(algorithm)
            .withIssuer(issuer)
            .build()
    }

    private fun getProps(): Properties {
        val configPath = environment.config
            .property(JWT_CONFIG_KEY)
            .getString()

        val propsFile = File(configPath)

        try {
            val inputStream = if (propsFile.isFile) {
                FileInputStream(propsFile)
            } else {
                throw Exception()
            }

            val props = Properties()
            props.load(inputStream)

            return props
        } catch (exception: Exception) {
            throw Exception()
        }
    }

    /**
     * Produce a token for this combination of User and Account
     */
    fun makeToken(user: User): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withAudience(audience)
        .withClaim("id", user.id)
        .withClaim("email", user.email)
        .withClaim("type", user.type.toString())
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}

// # Notes:

// ## setup array claim:

// ```
// JWT.create()
//      .withArrayClaim("countries", user.countries.toTypedArray())
// ```

