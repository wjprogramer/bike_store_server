package com.giant_giraffe.services.sales.order_item

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.dao.sales.OrderItemDao
import com.giant_giraffe.data.production.product.ProductTable
import com.giant_giraffe.data.sales.order.OrderTable
import com.giant_giraffe.data.sales.order_item.OrderItem
import com.giant_giraffe.data.sales.order_item.OrderItemEntity
import com.giant_giraffe.data.sales.order_item.OrderItemTable
import com.giant_giraffe.data.sales.order_item.OrderItemView
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
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

    override fun getList(page: Int, size: Int): PageableData<OrderItem> {
        return dao.getList(page, size)
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