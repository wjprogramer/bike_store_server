package com.giant_giraffe.services.sales.order_item

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.sales.customer.CustomerView
import com.giant_giraffe.data.sales.order_item.OrderItem
import com.giant_giraffe.data.sales.order_item.OrderItemView

interface OrderItemService {

    fun create(orderItem: OrderItem): OrderItem

    fun getById(orderItemId: Int): OrderItem?

    fun getList(page: Int, size: Int): PageableData<OrderItem>

    fun update(orderItem: OrderItem): Int

    fun delete(orderItemId: Int): Boolean

}