package com.giant_giraffe.services.sales.order

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.sales.order.Order
import com.giant_giraffe.enums.OrderStatus

interface OrderService {

    fun create(order: Order): Order

    fun getById(orderId: Int): Order?

    fun find(
        page: Int,
        size: Int,
        orderStatus: OrderStatus? = null,
        customerId: Int? = null,
        storeId: Int? = null,
        staffId: Int? = null,
    ): PagedData<Order>

    fun update(order: Order): Int

    fun delete(orderId: Int): Boolean

}