package com.giant_giraffe.services.production.stock

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.production.stock.Stock
import com.giant_giraffe.data.production.stock.StockView
import com.giant_giraffe.data.sales.customer.CustomerView

interface StockService {

    fun create(stock: Stock): StockView

    fun getById(stockId: Int): StockView?

    fun getList(page: Int, size: Int): PageableData<StockView>

    fun update(stock: Stock): Int

    fun delete(stockId: Int): Boolean

}