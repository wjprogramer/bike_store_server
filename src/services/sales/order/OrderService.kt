package com.giant_giraffe.services.sales.order

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.sales.customer.CustomerView
import com.giant_giraffe.data.sales.order.Order
import com.giant_giraffe.data.sales.order.OrderView

interface OrderService {

    fun create(order: Order): Order

    fun getById(orderId: Int): Order?

    fun getList(page: Int, size: Int): PageableData<Order>

    fun update(order: Order): Int

    fun delete(orderId: Int): Boolean

}