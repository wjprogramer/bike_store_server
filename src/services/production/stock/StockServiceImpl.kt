package com.giant_giraffe.services.production.stock

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.production.StockDao
import com.giant_giraffe.data.production.stock.Stock
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class StockServiceImpl: StockService {

    private val dao = StockDao

    override fun create(stock: Stock): Stock {
        if (
            stock.quantity == null ||
            stock.storeId == null ||
            stock.productId == null
        ) {
            throw Exception("")
        }

        return dao.create(stock)
    }

    override fun getById(stockId: Int): Stock? {
        return dao.getById(stockId)
    }

    override fun getList(page: Int, size: Int): PagedData<Stock> {
        return dao.getList(page, size)
    }

    override fun update(stock: Stock): Int {
        return dao.update(stock)
    }

    override fun delete(stockId: Int): Boolean {
        var result = true

        transaction {
            val number = dao.delete(stockId)
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }
    
}