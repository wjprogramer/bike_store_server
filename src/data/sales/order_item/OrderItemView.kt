package com.giant_giraffe.data.sales.order_item

data class OrderItemView(
    var id:         Int?        = null,
    var orderId:    Int?        = null,
    var quantity:   Int?        = null,
    var listPrice:  String?     = null,
    var discount:   String?     = null,
    var productId:  Int?        = null,
)