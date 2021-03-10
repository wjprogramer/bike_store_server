package com.giant_giraffe.data.sales.order

import com.giant_giraffe.data.BaseEntity
import com.giant_giraffe.data.sales.customer.CustomerEntity
import com.giant_giraffe.data.sales.order_item.OrderItemEntity
import com.giant_giraffe.data.sales.order_item.OrderItemTable
import com.giant_giraffe.data.sales.staff.StaffEntity
import com.giant_giraffe.data.sales.store.StoreEntity
import com.giant_giraffe.data.sales.store.StoreTable
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

    val customer by CustomerEntity optionalReferencedOn OrderTable.customerId
    val store by StoreEntity referencedOn OrderTable.storeId
    val staff by StaffEntity referencedOn OrderTable.staffId
    val orderItems by OrderItemEntity.referrersOn(OrderItemTable.orderId)

    override fun toModel() =
        Order(this)

    fun toDetailsModel(
        hasOrderItems: Boolean = false,
    ): Order {
        val order = Order(this)

        if (hasOrderItems) {
            order.orderItems = orderItems.toList().map { it.toModel() }
        }

        order.customer = customer?.toModel()
        order.store = store.toModel()
        order.staff = staff.toModel()

        return order
    }

}