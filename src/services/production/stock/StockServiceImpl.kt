package com.giant_giraffe.services.production.stock

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.production.product.ProductTable
import com.giant_giraffe.data.production.stock.Stock
import com.giant_giraffe.data.production.stock.StockEntity
import com.giant_giraffe.data.production.stock.StockTable
import com.giant_giraffe.data.production.stock.StockView
import com.giant_giraffe.data.sales.store.StoreTable
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

class StockServiceImpl: StockService {

    override fun create(stock: Stock): StockView {
        if (
            stock.quantity == null ||
            stock.storeId == null ||
            stock.productId == null
        ) {
            throw Exception("")
        }

        return transaction {
            StockEntity.new {
                quantity = stock.quantity
                storeId = EntityID(stock.storeId, StoreTable)
                productId = EntityID(stock.productId, ProductTable)
            }
        }.toView()
    }

    override fun getById(stockId: Int): StockView? {
        return transaction {
            StockEntity
                .find { StockTable.id eq stockId }
                .firstOrNull()
        }?.toView()
    }

    override fun getList(page: Int, size: Int): PageableData<StockView> {
        return transaction {
            val staffs = StockEntity.all()
                .limit(size, offset = page * size)
                .map { it.toView() }

            val pageInfo = EntityUtility
                .getPageInfo(StockEntity, page, size, staffs.size)

            PageableData(
                data = staffs,
                pageInfo = pageInfo
            )
        }
    }

    override fun update(stock: Stock): Int {
        return transaction {
            StockEntity
                .find { StockTable.id eq stock.id }
                .firstOrNull() ?: throw Exception()

            StockTable.update({ StockTable.id eq stock.id }) {
                stock.quantity?.let { e -> it[quantity] = e }
                stock.storeId?.let { e -> it[storeId] = EntityID(e, StoreTable) }
                stock.productId?.let { e -> it[productId] = EntityID(e, ProductTable) }
            }
        }
    }

    override fun delete(stockId: Int): Boolean {
        var result = true

        transaction {
            val number = StockTable.deleteWhere { StockTable.id eq stockId }
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }
    
}