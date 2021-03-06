package com.giant_giraffe.data.sales.store

import org.jetbrains.exposed.dao.IntIdTable

object StoreTable: IntIdTable("stores") {
    val storeName = varchar("store_name", 255)
    val phone = varchar("phone", 25).nullable()
    val email = varchar("email", 255).nullable()
    val street = varchar("street", 255).nullable()
    val city = varchar("city", 255).nullable()
    val state = varchar("state", 10).nullable()
    val zipCode = varchar("zip_code", 5).nullable()
}