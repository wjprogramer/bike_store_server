package com.giant_giraffe.data.sales.order_item

import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class OrderItemEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<OrderItem, OrderItemView> {

    companion object: IntEntityClass<OrderItemEntity>(OrderItemTable)

    var orderId by OrderItemTable.orderId
    var quantity by OrderItemTable.quantity
    var listPrice by OrderItemTable.listPrice
    var discount by OrderItemTable.discount
    var productId by OrderItemTable.productId

    override fun toModel() =
        OrderItem(this)

}