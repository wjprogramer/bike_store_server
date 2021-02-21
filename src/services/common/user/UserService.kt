package com.giant_giraffe.services.common.user

import com.giant_giraffe.data.common.EmailPasswordCredential
import com.giant_giraffe.data.common.User
import com.giant_giraffe.enums.UserType

interface UserService {

    fun findUserByCredentials(credential: EmailPasswordCredential, userType: UserType): User

}