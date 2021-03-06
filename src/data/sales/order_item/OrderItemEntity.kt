package com.giant_giraffe.data.sales.order_item

import com.giant_giraffe.data.BaseEntity
import com.giant_giraffe.data.production.product.ProductEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class OrderItemEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<OrderItem, OrderItemView> {

    companion object: IntEntityClass<OrderItemEntity>(OrderItemTable)

    var orderId     by OrderItemTable.orderId
    var quantity    by OrderItemTable.quantity
    var listPrice   by OrderItemTable.listPrice
    var discount    by OrderItemTable.discount
    var productId   by OrderItemTable.productId

    val product     by ProductEntity optionalReferencedOn OrderItemTable.productId

    override fun toModel() =
        OrderItem(this)

    fun toDetailsModel(): OrderItem {
        val orderItem = OrderItem(this)

        orderItem.product = product?.toModel()

        return orderItem
    }

}