package com.giant_giraffe.services.production.stock

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.production.stock.Stock

interface StockService {

    fun create(stock: Stock): Stock

    fun getById(stockId: Int): Stock?

    fun find(
        page: Int,
        size: Int,
        storeId: Int? = null,
        productId: Int? = null,
    ): PagedData<Stock>

    fun update(stock: Stock): Int

    fun delete(stockId: Int): Boolean

}