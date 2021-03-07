package com.giant_giraffe.data.production.stock

import com.giant_giraffe.data.BaseEntity
import com.giant_giraffe.data.production.product.ProductEntity
import com.giant_giraffe.data.sales.store.StoreEntity
import org.jetbrains.exposed.dao.*

class StockEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<Stock, StockView> {

    companion object: IntEntityClass<StockEntity>(StockTable)

    var quantity    by StockTable.quantity
    var storeId     by StockTable.storeId
    var productId   by StockTable.productId

    var product     by ProductEntity referencedOn StockTable.productId
    var store       by StoreEntity optionalReferencedOn StockTable.storeId

    override fun toModel(): Stock {
        val stock = Stock(this)

        stock.product = product.toModel()
        stock.store = store?.toModel()

        return stock
    }


}