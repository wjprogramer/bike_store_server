package com.giant_giraffe.data.production.stock

import com.google.gson.annotations.SerializedName

data class StockView(
    var id: Int? = null,
    var quantity: Int? = null,
    @SerializedName("store_id")
    var storeId: Int? = null,
    @SerializedName("product_id")
    var productId: Int? = null,
)