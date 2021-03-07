package com.giant_giraffe.data.sales.customer

import org.jetbrains.exposed.dao.IntIdTable

object CustomerTable: IntIdTable("customers") {
    val firstName =     varchar("first_name", 255)
    val lastName =      varchar("last_name", 255)
    val email =         varchar("email", 255)
    val password =      text("password")
    val phone =         varchar("phone", 25).nullable()
    val street =        varchar("street", 255).nullable()
    val city =          varchar("city", 50).nullable()
    val state =         varchar("state", 25).nullable()
    val zipCode =       varchar("zip_code", 5).nullable()
}