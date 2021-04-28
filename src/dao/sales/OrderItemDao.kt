package com.giant_giraffe.dao.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.BaseDao
import com.giant_giraffe.dao.production.ProductDao.findAndGetPagedData
import com.giant_giraffe.data.production.product.ProductTable
import com.giant_giraffe.data.sales.order.OrderTable
import com.giant_giraffe.data.sales.order_item.OrderItem
import com.giant_giraffe.data.sales.order_item.OrderItemEntity
import com.giant_giraffe.data.sales.order_item.OrderItemTable
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

object OrderItemDao:
    BaseDao<Int, OrderItemEntity, OrderItem>()
{

    fun create(
        orderItem: OrderItem,
    ): OrderItem {
        return transaction {
            OrderItemEntity.new(orderItem.id) {
                orderId = EntityID(orderItem.orderId, OrderTable)
                quantity = orderItem.quantity!!
                listPrice = orderItem.listPrice!!
                discount = orderItem.discount!!
                productId = EntityID(orderItem.productId, ProductTable)
            }.toModel()
        }
    }

    fun getById(orderId: Int, orderItemId: Int): OrderItem? {
        return transaction {
            OrderItemEntity
                .find {
                    OrderItemTable.orderId eq orderId and
                            (OrderItemTable.id eq orderItemId)
                }
                .firstOrNull()
                ?.toDetailsModel()
        }
    }

    fun find(page: Int,
             size: Int,
             orderId: Int? = null): PagedData<OrderItem> {
        var predicates: Op<Boolean> = Op.build { Op.TRUE }

        orderId?.let { predicates = predicates and (OrderItemTable.orderId eq orderId) }

        return OrderItemEntity.findAndGetPagedData(
            page = page,
            size = size,
            predicates = predicates,
            order = arrayOf(OrderItemTable.id to SortOrder.ASC)
        ) { entity ->
            entity.toDetailsModel()
        }
    }

    fun update(orderItem: OrderItem): Int {
        return transaction {
            OrderItemEntity
                .find { OrderItemTable.id eq orderItem.id }
                .firstOrNull() ?: throw Exception()

            OrderItemTable.update({
                OrderItemTable.orderId eq orderItem.orderId and
                        (OrderItemTable.id eq orderItem.id)
            }) {
                orderItem.quantity?.let     { e -> it[quantity]     = e }
                orderItem.listPrice?.let    { e -> it[listPrice]    = e }
                orderItem.discount?.let     { e -> it[discount]     = e }
                orderItem.productId?.let    { e -> it[productId]    = EntityID(e, ProductTable) }
            }
        }
    }

    fun delete(orderId: Int, orderItemId: Int): Int {
        return transaction {
            OrderItemTable.deleteWhere {
                OrderItemTable.orderId eq orderId and
                        (OrderItemTable.id eq orderItemId)
            }
        }
    }

}