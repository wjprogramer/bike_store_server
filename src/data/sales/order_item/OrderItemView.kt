package com.giant_giraffe.data.sales.order_item

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemView(
    var id: Int? = null,
    var orderId: Int? = null,
    var quantity: Int? = null,
    var listPrice: Int? = null,
    var discount: Int? = null,
    var productId: Int? = null,
)