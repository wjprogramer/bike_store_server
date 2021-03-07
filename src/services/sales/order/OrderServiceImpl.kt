package com.giant_giraffe.services.sales.order

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.sales.OrderDao
import com.giant_giraffe.data.sales.order.Order
import com.giant_giraffe.services.sales.order_item.OrderItemService
import io.ktor.application.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.instance
import org.kodein.di.ktor.di
import java.lang.Exception

class OrderServiceImpl(application: Application): OrderService {

    private val orderItemService by application.di().instance<OrderItemService>()

    private val dao = OrderDao

    override fun create(order: Order): Order {
        if (
            order.orderStatus == null ||
            order.orderDate == null ||
            order.requiredDate == null
        ) {
            throw Exception("")
        }

        return transaction {
            val createdOrder = dao.create(order)

            var orderItemId = 1
            order.orderItems?.forEach { orderItem ->
                orderItem.orderId = createdOrder.id
                orderItem.id = orderItemId
                orderItemId = (orderItemService.create(orderItem).id ?: orderItemId) + 1
            }

            getById(createdOrder.id!!)!!
        }
    }

    override fun getById(orderId: Int): Order? {
        return dao.getById(orderId)
    }

    override fun find(page: Int, size: Int): PagedData<Order> {
        return dao.find(page, size)
    }

    override fun update(order: Order): Int {
        return dao.update(order)
    }

    override fun delete(orderId: Int): Boolean {
        var result = true

        transaction {
            val number = dao.delete(orderId)
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }

}