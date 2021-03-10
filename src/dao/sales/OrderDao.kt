package com.giant_giraffe.dao.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.BaseDao
import com.giant_giraffe.data.sales.customer.CustomerTable
import com.giant_giraffe.data.sales.order.Order
import com.giant_giraffe.data.sales.order.OrderEntity
import com.giant_giraffe.data.sales.order.OrderTable
import com.giant_giraffe.data.sales.staff.StaffTable
import com.giant_giraffe.data.sales.store.StoreTable
import com.giant_giraffe.enums.OrderStatus
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNull
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

object OrderDao:
    BaseDao<Int, OrderEntity, Order>()
{

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
            }.toModel()
        }
    }

    fun getById(orderId: Int): Order? {
        return transaction {
            OrderEntity
                .find { OrderTable.id eq orderId }
                .firstOrNull()
                ?.load(OrderEntity::orderItems)
                ?.toDetailsModel(
                    hasOrderItems = true
                )
        }
    }

    fun find(
        page: Int,
        size: Int,
        orderStatus: OrderStatus?,
        customerId: Int?,
        storeId: Int?,
        staffId: Int?,
    ): PagedData<Order> {
        var predicates: Op<Boolean> = Op.build { Op.TRUE }
        val needLoadCustomer = customerId == null
        val needLoadStore = storeId == null
        val needLoadStaff = staffId == null

        orderStatus?.let { predicates = predicates and (OrderTable.orderStatus eq orderStatus ) }

        customerId?.let {
            predicates = predicates and (
                    OrderTable.customerId.isNull() or
                            (OrderTable.customerId eq EntityID(customerId, CustomerTable))
                    )
        }
        storeId?.let { predicates = predicates and (OrderTable.storeId eq it) }
        staffId?.let { predicates = predicates and (OrderTable.staffId eq it) }

        return OrderEntity.findAndGetPagedData(
            page = page,
            size = size,
            predicates = predicates
        ) { entity ->
            entity.toDetailsModel()
        }
    }

    fun update(order: Order): Int {
        return transaction {
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