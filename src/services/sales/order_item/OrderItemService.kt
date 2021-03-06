package com.giant_giraffe.services.sales.order_item

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.sales.order_item.OrderItem

interface OrderItemService {

    fun create(orderItem: OrderItem): OrderItem

    fun getById(orderItemId: Int): OrderItem?

    fun getList(page: Int, size: Int): PagedData<OrderItem>

    fun update(orderItem: OrderItem): Int

    fun delete(orderItemId: Int): Boolean

}