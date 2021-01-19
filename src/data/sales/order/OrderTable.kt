package com.giant_giraffe.data.sales.order

import com.giant_giraffe.data.sales.customer.CustomerTable
import com.giant_giraffe.data.sales.staff.StaffTable
import com.giant_giraffe.data.sales.store.StoreTable
import com.giant_giraffe.enums.OrderStatus
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object OrderTable: IntIdTable("orders") {
    val orderStatus = enumerationByName("order_status", 50, OrderStatus::class)
    val orderDate = datetime("order_date")
    val requiredDate = datetime("required_date")
    val shippedDate = datetime("shipped_date").nullable()

    val customerId = reference(
        "customer_id",
        CustomerTable.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    ).nullable()

    val storeId = reference(
        "store_id",
        StoreTable.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )

    val staffId = reference(
        "staff_id",
        StaffTable.id,
        onDelete = ReferenceOption.NO_ACTION,
        onUpdate = ReferenceOption.NO_ACTION
    )
}