package com.giant_giraffe.dao.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.production.product.ProductTable
import com.giant_giraffe.data.production.stock.Stock
import com.giant_giraffe.data.production.stock.StockEntity
import com.giant_giraffe.data.production.stock.StockTable
import com.giant_giraffe.data.sales.store.StoreTable
import com.giant_giraffe.utility.EntityUtility
import com.giant_giraffe.utils.tryAnd
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.or
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

    fun getList(page: Int, size: Int,
                productId: Int? = null,
                storeId: Int? = null,
    ): PagedData<Stock> {
        return transaction {

            var predicates: Op<Boolean> = Op.build { Op.TRUE }

            predicates = predicates.tryAnd(productId) { StockTable.productId eq productId }
            predicates = predicates.tryAnd(storeId) {
                StockTable.storeId.isNull() or
                        (StockTable.storeId eq EntityID(storeId, StoreTable))
            }

            val stocks = StockEntity
                .find(predicates)

            val pagedStocks = stocks
                .limit(size, offset = page * size)
                .map {
                    it
                        .load(StockEntity::product)
                        .load(StockEntity::store)
                        .toModel()
                }

            PagedData(
                data = pagedStocks,
                pageInfo = EntityUtility.getPageInfo(
                    size = size,
                    page = page,
                    elements = pagedStocks.size,
                    totalElements = stocks.count(),
                )
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