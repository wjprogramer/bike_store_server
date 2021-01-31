package com.giant_giraffe.services.sales.order

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.dao.sales.OrderDao
import com.giant_giraffe.data.sales.customer.CustomerTable
import com.giant_giraffe.data.sales.order.Order
import com.giant_giraffe.data.sales.order.OrderEntity
import com.giant_giraffe.data.sales.order.OrderTable
import com.giant_giraffe.data.sales.order.OrderView
import com.giant_giraffe.data.sales.staff.StaffTable
import com.giant_giraffe.data.sales.store.StoreTable
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
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

    override fun getList(page: Int, size: Int): PageableData<Order> {
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