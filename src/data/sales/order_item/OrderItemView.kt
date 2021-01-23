package com.giant_giraffe.data.sales.order_item

import com.google.gson.annotations.SerializedName

data class OrderItemView(
    var id: Int? = null,
    @SerializedName("order_id")
    var orderId: Int? = null,
    var quantity: Int? = null,
    @SerializedName("list_price")
    var listPrice: String? = null,
    var discount: String? = null,
    @SerializedName("product_id")
    var productId: Int? = null,
)