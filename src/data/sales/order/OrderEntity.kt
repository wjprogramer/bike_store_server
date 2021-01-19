package com.giant_giraffe.data.sales.order

import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class OrderEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<Order, OrderView> {

    companion object: IntEntityClass<OrderEntity>(OrderTable)

    override fun toModel() =
        Order(this)

}