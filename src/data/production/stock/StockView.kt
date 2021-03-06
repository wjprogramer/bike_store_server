package com.giant_giraffe.data.production.stock

import com.giant_giraffe.data.production.product.ProductView
import com.giant_giraffe.data.sales.store.StoreView
import com.google.gson.annotations.SerializedName

data class StockView(
    var id: Int? = null,
    var quantity: Int? = null,
    @SerializedName("store_id")
    var storeId: Int? = null,
    @SerializedName("product_id")
    var productId: Int? = null,
    var product: ProductView? = null,
    var store: StoreView? = null,
)