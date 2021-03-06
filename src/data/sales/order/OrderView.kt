package com.giant_giraffe.data.sales.order

import com.giant_giraffe.data.sales.order_item.OrderItemView
import com.google.gson.annotations.SerializedName

data class OrderView(
    var id: Int? = null,
    @SerializedName("order_status")
    var orderStatus: String? = null,
    @SerializedName("order_date")
    var orderDate: String? = null,
    @SerializedName("required_date")
    var requiredDate: String? = null,
    @SerializedName("shipped_date")
    var shippedDate: String? = null,
    @SerializedName("customer_id")
    var customerId: Int? = null,
    @SerializedName("store_id")
    var storeId: Int? = null,
    @SerializedName("staff_id")
    var staffId: Int? = null,

    // Relation
    @SerializedName("order_items")
    var orderItems: List<OrderItemView>? = null,
)