package com.giant_giraffe.dao.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.BaseDao
import com.giant_giraffe.data.production.product.ProductTable
import com.giant_giraffe.data.production.stock.Stock
import com.giant_giraffe.data.production.stock.StockEntity
import com.giant_giraffe.data.production.stock.StockTable
import com.giant_giraffe.data.sales.store.StoreTable
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNull
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

object StockDao:
    BaseDao<Int, StockEntity, Stock>()
{

    fun create(stock: Stock): Stock {
        return transaction {
            StockEntity.new {
                quantity = stock.quantity
                storeId = EntityID(stock.storeId, StoreTable)
                productId = EntityID(stock.productId, ProductTable)
            }.toModel()
        }
    }

    fun getById(stockId: Int): Stock? {
        return transaction {
            StockEntity
                .find { StockTable.id eq stockId }
                .firstOrNull()
                ?.load(StockEntity::product)
                ?.load(StockEntity::store)
                ?.toDetailsModel()
        }
    }

    fun find(page: Int,
             size: Int,
             productId: Int? = null,
             storeId: Int? = null,
    ): PagedData<Stock> {
        var predicates: Op<Boolean> = Op.build { Op.TRUE }
        val needLoadProductModel = productId == null
        val needLoadStoreModel = storeId == null

        productId?.let {
            predicates = predicates and (StockTable.productId eq productId)
        }
        storeId?.let {
            predicates = predicates and (
                    StockTable.storeId eq EntityID(storeId, StoreTable) or
                        StockTable.storeId.isNull()
                    )
        }

        return StockEntity.findAndGetPagedData(
            page = page,
            size = size,
            predicates = predicates,
            order = arrayOf(StockTable.id to SortOrder.ASC)
        ) { entity ->
            if (needLoadProductModel) {
                entity.load(StockEntity::product)
            }
            if (needLoadStoreModel) {
                entity.load(StockEntity::store)
            }

            entity.toDetailsModel(
                hasProduct = needLoadProductModel,
                hasStore = needLoadStoreModel,
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