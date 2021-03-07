package com.giant_giraffe.data.sales.order

import com.giant_giraffe.data.BaseEntity
import com.giant_giraffe.data.sales.order_item.OrderItemEntity
import com.giant_giraffe.data.sales.order_item.OrderItemTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class OrderEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<Order, OrderView> {

    companion object: IntEntityClass<OrderEntity>(OrderTable)

    var orderStatus     by OrderTable.orderStatus
    var orderDate       by OrderTable.orderDate
    var requiredDate    by OrderTable.requiredDate
    var shippedDate     by OrderTable.shippedDate
    var customerId      by OrderTable.customerId
    var storeId         by OrderTable.storeId
    var staffId         by OrderTable.staffId

    val orderItems by OrderItemEntity.referrersOn(OrderItemTable.order)

    override fun toModel() =
        Order(this)

    fun toDetailsModel(): Order {
        val order = Order(this)

        order.orderItems = orderItems.toList().map { it.toModel() }

        return order
    }

}