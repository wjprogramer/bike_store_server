package com.giant_giraffe.data.sales.order

import com.giant_giraffe.data.BaseModel

class Order(orderEntity: OrderEntity): BaseModel<OrderView> {

    var id = orderEntity.id.value
    var orderStatus = orderEntity.orderStatus
    var orderDate = orderEntity.orderDate
    var requiredDate = orderEntity.requiredDate
    var shippedDate = orderEntity.shippedDate
    var customerId = orderEntity.customerId?.value
    var storeId = orderEntity.storeId.value
    var staffId = orderEntity.staffId.value

    override fun toView(): OrderView {
        return OrderView(
            id,
            orderStatus,
            orderDate.toString(),
            requiredDate.toString(),
            shippedDate?.toString(),
            customerId,
            storeId,
            staffId,
        )
    }

}