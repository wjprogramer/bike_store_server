package com.giant_giraffe.services.sales.order_item

import com.giant_giraffe.core.PageableData
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
    override fun create(orderItem: OrderItem): OrderItemView {
        if (
            orderItem.quantity == null ||
            orderItem.listPrice == null ||
            orderItem.discount == null
        ) {
            throw Exception("")
        }

        return transaction {
            OrderItemEntity.new {
                orderId = EntityID(orderItem.orderId, OrderTable)
                quantity = orderItem.quantity!!
                listPrice = orderItem.listPrice!!
                discount = orderItem.discount!!
                productId = EntityID(orderItem.productId, ProductTable)
            }
        }.toView()
    }

    override fun getById(orderItemId: Int): OrderItemView? {
        return transaction {
            OrderItemEntity
                .find { OrderItemTable.id eq orderItemId }
                .firstOrNull()
        }?.toView()
    }

    override fun getList(page: Int, size: Int): PageableData<OrderItemView> {
        return transaction {
            val staffs = OrderItemEntity.all()
                .limit(size, offset = page * size)
                .map { it.toView() }

            val pageInfo = EntityUtility
                .getPageInfo(OrderItemEntity, page, size, staffs.size)

            PageableData(
                data = staffs,
                pageInfo = pageInfo
            )
        }
    }

    override fun update(orderItem: OrderItem): Int {
        return transaction {
            OrderItemEntity
                .find { OrderItemTable.id eq orderItem.id }
                .firstOrNull() ?: throw Exception()

            OrderItemTable.update({ OrderItemTable.id eq orderItem.id }) {
                orderItem.orderId?.let { e -> it[orderId] = EntityID(e, OrderTable) }
                orderItem.quantity?.let { e -> it[quantity] = e }
                orderItem.listPrice?.let { e -> it[listPrice] = e }
                orderItem.discount?.let { e -> it[discount] = e }
                orderItem.productId?.let { e -> it[productId] = EntityID(e, ProductTable) }
            }
        }
    }

    override fun delete(orderItemId: Int): Boolean {
        var result = true

        transaction {
            val number = OrderItemTable.deleteWhere { OrderItemTable.id eq orderItemId }
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }
}