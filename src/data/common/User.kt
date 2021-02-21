package com.giant_giraffe.data.common

import com.giant_giraffe.enums.UserType
import io.ktor.auth.*

data class User(
    val id: Int?,
    val email: String?,
    val type: UserType,
) : Principal