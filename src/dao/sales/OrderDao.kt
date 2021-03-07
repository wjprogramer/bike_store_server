package com.giant_giraffe.dao.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.sales.customer.CustomerTable
import com.giant_giraffe.data.sales.order.Order
import com.giant_giraffe.data.sales.order.OrderEntity
import com.giant_giraffe.data.sales.order.OrderTable
import com.giant_giraffe.data.sales.staff.StaffTable
import com.giant_giraffe.data.sales.store.StoreTable
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

object OrderDao {

    fun create(order: Order): Order {
        return transaction {
            OrderEntity.new {
                orderStatus = order.orderStatus!!
                orderDate = order.orderDate!!
                requiredDate = order.requiredDate!!
                shippedDate = order.shippedDate
                customerId = EntityID(order.customerId, CustomerTable)
                storeId = EntityID(order.storeId, StoreTable)
                staffId = EntityID(order.staffId, StaffTable)
            }
        }.toModel()
    }

    fun getById(orderId: Int): Order? {
        return transaction {
            val order = OrderEntity
                .find { OrderTable.id eq orderId }
                .firstOrNull()
                ?.load(OrderEntity::orderItems)

            order?.orderItems
            order?.toDetailsModel()
        }
    }

    fun find(page: Int, size: Int): PagedData<Order> {
        var totalDataSize = 0

        val orders = transaction {
            val allData = OrderEntity.all()
            totalDataSize = allData.count()

            allData
                .limit(size, offset = page * size)
                .map { it.toModel() }
        }

        val pageInfo = EntityUtility.getPageInfo(
            size = size,
            page = page,
            dataCount = orders.size,
            totalDataCount = totalDataSize,
        )

        return PagedData(
            data = orders,
            pageInfo = pageInfo
        )
    }

    fun update(order: Order): Int {
        return transaction {
            OrderEntity
                .find { OrderTable.id eq order.id }
                .firstOrNull() ?: throw Exception()

            OrderTable.update({ OrderTable.id eq order.id }) {
                order.orderStatus?.let { e -> it[orderStatus] = e }
                order.orderDate?.let { e -> it[orderDate] = e }
                order.requiredDate?.let { e -> it[requiredDate] = e }
                order.shippedDate?.let { e -> it[shippedDate] = e }
                order.customerId?.let { e -> it[customerId] = EntityID(e, CustomerTable) }
                order.storeId?.let { e -> it[storeId] = EntityID(e, StoreTable) }
                order.staffId?.let { e -> it[staffId] = EntityID(e, StaffTable) }
            }
        }
    }

    fun delete(orderId: Int): Int {
        return transaction {
            OrderTable.deleteWhere { OrderTable.id eq orderId }
        }
    }

}