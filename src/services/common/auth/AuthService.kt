package com.giant_giraffe.services.common.auth

import com.giant_giraffe.data.common.User

interface AuthService {

    fun makeToken(user: User): String

}