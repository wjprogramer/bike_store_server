package com.giant_giraffe.services.common.user

import com.giant_giraffe.data.common.EmailPasswordCredential
import com.giant_giraffe.data.common.User
import com.giant_giraffe.enums.UserType
import com.giant_giraffe.services.sales.customer.CustomerService
import com.giant_giraffe.services.sales.staff.StaffService
import com.giant_giraffe.utility.PasswordUtility
import io.ktor.application.*
import org.kodein.di.instance
import org.kodein.di.ktor.di
import java.lang.Exception

class UserServiceImpl(private val application: Application): UserService {

    private val customerService by application.di().instance<CustomerService>()
    private val staffService by application.di().instance<StaffService>()

    override fun findUserByCredentials(credential: EmailPasswordCredential, userType: UserType): User {
        try {
            val validateInfo = when(userType) {
                UserType.STAFF -> {
                    getStaffUserByCredential(credential)
                }
                UserType.CUSTOMER -> {
                    getCustomerUserByCredential(credential)
                }
                UserType.UNKNOWN -> {
                    throw Exception()
                }
            }

            val encodedPassword = PasswordUtility.encode(credential.password)
            if (encodedPassword != validateInfo.password) {
                throw Exception()
            }

            return validateInfo.user
        } catch(e: Exception) {
            throw e
        }
    }

    private fun getCustomerUserByCredential(credential: EmailPasswordCredential): ValidateInfo {
        try {
            val email = credential.email
            val customer = customerService.getByEmail(email) ?: throw Exception()

            return ValidateInfo(
                User(
                    id = customer.id,
                    email = customer.email,
                    type = UserType.STAFF,
                ),
                password = customer.password,
            )
        } catch (e: Exception) {
            throw e
        }
    }

    private fun getStaffUserByCredential(credential: EmailPasswordCredential): ValidateInfo {
        try {
            val email = credential.email
            val staff = staffService.getByEmail(email) ?: throw Exception()

            return ValidateInfo(
                User(
                    id = staff.id,
                    email = staff.email,
                    type = UserType.STAFF,
                ),
                password = staff.password,
            )
        } catch (e: Exception) {
            throw e
        }
    }

    inner class ValidateInfo(
        val user: User,
        val password: String?,
    )

}

//    private val users = listOf(testUser).associateBy(User::id)