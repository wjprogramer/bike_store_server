package com.giant_giraffe.data.sales.order

import com.giant_giraffe.enums.OrderStatus
import kotlinx.serialization.Serializable

@Serializable
data class OrderView(
    var id: Int? = null,
    var orderStatus: OrderStatus? = null,
    var orderDate: String? = null,
    var requiredDate: String? = null,
    var shippedDate: String? = null,
    var customerId: Int? = null,
    var storeId: Int? = null,
    var staffId: Int? = null,
)