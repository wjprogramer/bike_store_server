package com.giant_giraffe.services.sales.order_item

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.sales.OrderItemDao
import com.giant_giraffe.data.sales.order_item.OrderItem
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class OrderItemServiceImpl: OrderItemService {

    private val dao = OrderItemDao

    override fun create(orderItem: OrderItem): OrderItem {
        if (
            orderItem.quantity == null ||
            orderItem.listPrice == null ||
            orderItem.discount == null
        ) {
            throw Exception("")
        }

        return dao.create(orderItem)
    }

    override fun getById(orderItemId: Int): OrderItem? {
        return dao.getById(orderItemId)
    }

    override fun find(page: Int, size: Int): PagedData<OrderItem> {
        return dao.find(page, size)
    }

    override fun update(orderItem: OrderItem): Int {
        return dao.update(orderItem)
    }

    override fun delete(orderItemId: Int): Boolean {
        var result = true

        transaction {
            val number = dao.delete(orderItemId)
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }

}