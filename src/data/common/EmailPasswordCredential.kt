package com.giant_giraffe.data.common

import io.ktor.auth.*

data class EmailPasswordCredential(val email: String, val password: String) : Credential
