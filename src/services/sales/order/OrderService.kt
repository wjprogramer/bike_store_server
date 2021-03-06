package com.giant_giraffe.services.sales.order

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.sales.order.Order

interface OrderService {

    fun create(order: Order): Order

    fun getById(orderId: Int): Order?

    fun getList(page: Int, size: Int): PagedData<Order>

    fun update(order: Order): Int

    fun delete(orderId: Int): Boolean

}