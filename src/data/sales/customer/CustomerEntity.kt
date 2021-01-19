package com.giant_giraffe.data.sales.customer

import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class CustomerEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<Customer, CustomerView> {

    companion object: IntEntityClass<CustomerEntity>(CustomerTable)

    var firstName by CustomerTable.firstName
    var lastName by CustomerTable.lastName
    var email by CustomerTable.email
    var phone by CustomerTable.phone
    var street by CustomerTable.street
    var city by CustomerTable.city
    var state by CustomerTable.state
    var zipCode by CustomerTable.zipCode

    override fun toModel() =
        Customer(this)

}