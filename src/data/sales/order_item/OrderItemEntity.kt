package com.giant_giraffe.data.sales.order_item

import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class OrderItemEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<OrderItem, OrderItemView> {

    companion object: IntEntityClass<OrderItemEntity>(OrderItemTable)

    override fun toModel() =
        OrderItem(this)

}