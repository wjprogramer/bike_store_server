package com.giant_giraffe.services.sales.order

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.sales.customer.CustomerView
import com.giant_giraffe.data.sales.order.Order
import com.giant_giraffe.data.sales.order.OrderView

interface OrderService {

    fun create(order: Order): OrderView

    fun getById(orderId: Int): OrderView?

    fun getList(page: Int, size: Int): PageableData<OrderView>

    fun update(order: Order): Int

    fun delete(orderId: Int): Boolean

}