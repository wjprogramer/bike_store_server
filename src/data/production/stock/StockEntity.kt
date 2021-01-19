package com.giant_giraffe.data.production.stock

import com.giant_giraffe.core.ComparablePair
import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.*

class StockEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<Stock, StockView> {

    companion object: IntEntityClass<StockEntity>(StockTable)

    override fun toModel() =
        Stock(this)

}