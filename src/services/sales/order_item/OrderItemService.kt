package com.giant_giraffe.services.sales.order_item

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.sales.order_item.OrderItem

interface OrderItemService {

    fun create(orderItem: OrderItem): OrderItem

    fun getById(orderId: Int, orderItemId: Int): OrderItem?

    fun find(
        page: Int,
        size: Int,
        orderId: Int? = null
    ): PagedData<OrderItem>

    fun update(orderItem: OrderItem): Int

    fun delete(orderId: Int, orderItemId: Int): Boolean

}