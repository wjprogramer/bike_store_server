package com.giant_giraffe.data.sales.order

import com.giant_giraffe.data.BaseView
import com.giant_giraffe.data.sales.customer.CustomerView
import com.giant_giraffe.data.sales.order_item.OrderItemView
import com.giant_giraffe.data.sales.staff.StaffView
import com.giant_giraffe.data.sales.store.StoreView

data class OrderView(
    var id:             Int?        = null,
    var orderStatus:    String?     = null,
    var orderDate:      String?     = null,
    var requiredDate:   String?     = null,
    var shippedDate:    String?     = null,
    var customerId:     Int?        = null,
    var storeId:        Int?        = null,
    var staffId:        Int?        = null,

    // Relation
    var customer:       CustomerView? = null,
    var store:          StoreView? = null,
    var staff:          StaffView? = null,
    var orderItems: List<OrderItemView>? = null,
): BaseView