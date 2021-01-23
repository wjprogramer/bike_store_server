package com.giant_giraffe.services.sales.order

import com.giant_giraffe.core.PageableData
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

    override fun create(order: Order): OrderView {
        if (
            order.orderStatus == null ||
            order.orderDate == null ||
            order.requiredDate == null
        ) {
            throw Exception("")
        }

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
        }.toView()
    }

    override fun getById(orderId: Int): OrderView? {
        return transaction {
            OrderEntity
                .find { OrderTable.id eq orderId }
                .firstOrNull()
        }?.toView()
    }

    override fun getList(page: Int, size: Int): PageableData<OrderView> {
        return transaction {
            val staffs = OrderEntity.all()
                .limit(size, offset = page * size)
                .map { it.toView() }

            val pageInfo = EntityUtility
                .getPageInfo(OrderEntity, page, size, staffs.size)

            PageableData(
                data = staffs,
                pageInfo = pageInfo
            )
        }
    }

    override fun update(order: Order): Int {
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

    override fun delete(orderId: Int): Boolean {
        var result = true

        transaction {
            val number = OrderTable.deleteWhere { OrderTable.id eq orderId }
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }

}