package com.giant_giraffe.data.production.stock

import kotlinx.serialization.Serializable

@Serializable
data class StockView(
    var id: Int? = null,
    var quantity: Int? = null,
    var storeId: Int? = null,
    var productId: Int? = null,
)