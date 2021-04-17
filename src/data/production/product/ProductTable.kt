package com.giant_giraffe.data.production.product

import com.giant_giraffe.data.production.brand.BrandTable
import com.giant_giraffe.data.production.category.CategoryTable
import com.giant_giraffe.utils.exposed.array
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.StringColumnType
import org.jetbrains.exposed.sql.VarCharColumnType

object ProductTable: IntIdTable("products") {
    val name =      varchar("name", 255)
    val modelYear = integer("model_year")
    val listPrice = decimal("list_price", 10, 2)
    val imagesUrls = array<String>("images_urls", VarCharColumnType())
    val visible = bool("visible").default(true)
    val isDeleted = bool("is_deleted").default(false)

    val brandId = reference(
        "brand_id",
        BrandTable,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )

    val categoryId = reference(
        "category_id",
        CategoryTable,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
}