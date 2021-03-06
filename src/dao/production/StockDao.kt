package com.giant_giraffe.dao.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.production.product.ProductTable
import com.giant_giraffe.data.production.stock.Stock
import com.giant_giraffe.data.production.stock.StockEntity
import com.giant_giraffe.data.production.stock.StockTable
import com.giant_giraffe.data.sales.store.StoreTable
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

object StockDao {

    fun create(stock: Stock): Stock {
        return transaction {
            StockEntity.new {
                quantity = stock.quantity
                storeId = EntityID(stock.storeId, StoreTable)
                productId = EntityID(stock.productId, ProductTable)
            }
        }.toModel()
    }

    fun getById(stockId: Int): Stock? {
        return transaction {
            StockEntity
                .find { StockTable.id eq stockId }
                .firstOrNull()
        }?.toModel()
    }

    fun getList(page: Int, size: Int): PagedData<Stock> {
        return transaction {
            val staffs = StockEntity.all()
                .limit(size, offset = page * size)
                .map { it.toModel() }

            val pageInfo = EntityUtility
                .getPageInfo(StockEntity, page, size, staffs.size)

            PagedData(
                data = staffs,
                pageInfo = pageInfo
            )
        }
    }

    fun update(stock: Stock): Int {
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

    fun delete(stockId: Int): Int {
        return transaction {
            StockTable.deleteWhere { StockTable.id eq stockId }
        }
    }

}