package com.giant_giraffe.data.production.stock

import com.giant_giraffe.data.production.product.Product
import com.giant_giraffe.data.production.product.ProductTable
import com.giant_giraffe.data.sales.store.StoreTable
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * FIXME: 要想辦法不用 [IntIdTable]（這個會產生 id，但這張 table 不需要）
 */
object StockTable: IntIdTable("stocks") {
    val quantity = integer("quantity").nullable()

    val storeId = reference(
        "store_id",
        StoreTable,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    ).nullable()

    val productId = reference(
        "product_id",
        ProductTable,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )

    init {
        // Composite unique constraint, or use `uniqueIndex`
        index(true, storeId, productId)
    }

}