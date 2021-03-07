package com.giant_giraffe.data.production.stock

import com.giant_giraffe.data.production.product.ProductView
import com.giant_giraffe.data.sales.store.StoreView

data class StockView(
    var id:         Int?            = null,
    var quantity:   Int?            = null,
    var storeId:    Int?            = null,
    var productId:  Int?            = null,
    var product:    ProductView?    = null,
    var store:      StoreView?      = null,
)