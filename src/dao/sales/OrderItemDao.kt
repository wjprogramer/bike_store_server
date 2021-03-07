package com.giant_giraffe.dao.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.BaseDao
import com.giant_giraffe.data.production.product.ProductTable
import com.giant_giraffe.data.sales.order.OrderTable
import com.giant_giraffe.data.sales.order_item.OrderItem
import com.giant_giraffe.data.sales.order_item.OrderItemEntity
import com.giant_giraffe.data.sales.order_item.OrderItemTable
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

object OrderItemDao:
    BaseDao<Int, OrderItemEntity, OrderItem>()
{

    fun create(orderItem: OrderItem): OrderItem {
        return transaction {
            OrderItemEntity.new {
                orderId = EntityID(orderItem.orderId, OrderTable)
                quantity = orderItem.quantity!!
                listPrice = orderItem.listPrice!!
                discount = orderItem.discount!!
                productId = EntityID(orderItem.productId, ProductTable)
            }
        }.toModel()
    }

    fun getById(orderItemId: Int): OrderItem? {
        return transaction {
            OrderItemEntity
                .find { OrderItemTable.id eq orderItemId }
                .firstOrNull()
        }?.toModel()
    }

    fun find(page: Int, size: Int): PagedData<OrderItem> {
        var totalDataSize = 0

        val orderItems = transaction {
            val allData = OrderItemEntity.all()
            totalDataSize = allData.count()

            allData
                .limit(size, offset = page * size)
                .map { it.toModel() }
        }

        val pageInfo = EntityUtility.getPageInfo(
            size = size,
            page = page,
            dataCount = orderItems.size,
            totalDataCount = totalDataSize,
        )

        return PagedData(
            data = orderItems,
            pageInfo = pageInfo
        )
    }

    fun update(orderItem: OrderItem): Int {
        return transaction {
            OrderItemEntity
                .find { OrderItemTable.id eq orderItem.id }
                .firstOrNull() ?: throw Exception()

            OrderItemTable.update({ OrderItemTable.id eq orderItem.id }) {
                orderItem.orderId?.let { e -> it[order] = EntityID(e, OrderTable) }
                orderItem.quantity?.let { e -> it[quantity] = e }
                orderItem.listPrice?.let { e -> it[listPrice] = e }
                orderItem.discount?.let { e -> it[discount] = e }
                orderItem.productId?.let { e -> it[productId] = EntityID(e, ProductTable) }
            }
        }
    }

    fun delete(orderItemId: Int): Int {
        return transaction {
            OrderItemTable.deleteWhere { OrderItemTable.id eq orderItemId }
        }
    }

}