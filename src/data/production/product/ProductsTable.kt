package com.giant_giraffe.data.production.product

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object ProductsTable: IntIdTable("products") {
    val name = varchar("name", 255)
    val modelYear = integer("model_year")
    val listPrice = decimal("list_price", 10, 2)

    val brandId = reference(
        "brand_id", id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )

    val categoryId = reference(
        "category_id", id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
}