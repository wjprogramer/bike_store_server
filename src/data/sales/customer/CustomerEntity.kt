package com.giant_giraffe.data.sales.customer

import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class CustomerEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<Customer, CustomerView> {

    companion object: IntEntityClass<CustomerEntity>(CustomerTable)

    override fun toModel() =
        Customer(this)

}