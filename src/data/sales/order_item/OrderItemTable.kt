package com.giant_giraffe.data.sales.order_item

import com.giant_giraffe.data.production.product.ProductTable
import com.giant_giraffe.data.sales.order.OrderTable
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.math.BigDecimal

object OrderItemTable: IntIdTable("order_items") {
    val order = reference(
        "order_id",
        OrderTable,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    ).primaryKey()

    val quantity = integer("quantity")
    val listPrice = decimal("list_price", 10, 2)
    val discount = decimal("discount", 4, 2).default(BigDecimal.valueOf(0))

    val product = reference(
        "product_id",
        ProductTable,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    ).nullable()
}