package com.giant_giraffe.services.sales.order

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.sales.OrderDao
import com.giant_giraffe.data.sales.order.Order
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class OrderServiceImpl: OrderService {

    private val dao = OrderDao

    override fun create(order: Order): Order {
        if (
            order.orderStatus == null ||
            order.orderDate == null ||
            order.requiredDate == null
        ) {
            throw Exception("")
        }

        return dao.create(order)
    }

    override fun getById(orderId: Int): Order? {
        return dao.getById(orderId)
    }

    override fun getList(page: Int, size: Int): PagedData<Order> {
        return dao.getList(page, size)
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