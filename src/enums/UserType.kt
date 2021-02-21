package com.giant_giraffe.enums

enum class UserType(code: String) {
    CUSTOMER("customer"),
    STAFF("staff"),
    UNKNOWN("unknown")
}

fun String.toUserType() = when (this) {
    UserType.CUSTOMER.name -> UserType.CUSTOMER
    UserType.STAFF.name -> UserType.STAFF
    else -> UserType.UNKNOWN
}