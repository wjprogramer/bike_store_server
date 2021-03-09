package com.giant_giraffe.services.sales.order

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.sales.OrderDao
import com.giant_giraffe.data.sales.order.Order
import com.giant_giraffe.enums.OrderStatus
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

    override fun find(
        page: Int,
        size: Int,
        orderStatus: OrderStatus?,
        customerId: Int?,
        storeId: Int?,
        staffId: Int?,
    ): PagedData<Order> {
        return dao.find(
            page,
            size,
            orderStatus = orderStatus,
            customerId = customerId,
            storeId = storeId,
            staffId = staffId,
        )
    }

    override fun update(order: Order): Int {
        var result = 0

        if (order.id == null) {
            throw Exception()
        }

        // Process received data
        val newItems = order.orderItems?.toMutableList() ?: mutableListOf()
        val newItemIds = newItems.mapNotNull { it.id }

        transaction {
            val oldOrder = dao.getById(order.id!!) ?: throw Exception()

            // Prepare items
            val oldItemIds = oldOrder.orderItems?.mapNotNull { it.id } ?: listOf()
            val deleteItemIds = (oldItemIds + newItemIds)
                .groupBy { it }
                .filter { it.value.size == 1}
                .flatMap { it.value }

            newItems.removeIf { deleteItemIds.contains(it.id) }
            var nextOrderItemId = oldItemIds.maxOf { it } + 1

            // Update
            result = dao.update(order)

            // Delete Items
            deleteItemIds.forEach { itemId ->
                orderItemService.delete(oldOrder.id!! , itemId)
            }

            // Create or update Items
            newItems.forEach { orderItem ->
                if (orderItem.id != null) {
                    orderItemService.update(orderItem)
                } else {
                    orderItem.id = nextOrderItemId
                    orderItem.orderId = oldOrder.id
                    orderItemService.create(orderItem)
                    nextOrderItemId++
                }
            }

        }
        return result
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