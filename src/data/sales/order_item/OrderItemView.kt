package com.giant_giraffe.data.sales.order_item

import com.giant_giraffe.data.BaseView
import com.giant_giraffe.data.production.product.ProductView

data class OrderItemView(
    var id:         Int?            = null,
    var orderId:    Int?            = null,
    var quantity:   Int?            = null,
    var listPrice:  String?         = null,
    var discount:   String?         = null,
    var productId:  Int?            = null,
    var product:    ProductView?    = null,
): BaseView