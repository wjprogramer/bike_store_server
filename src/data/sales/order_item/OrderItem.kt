package com.giant_giraffe.data.sales.order_item

import com.giant_giraffe.data.BaseModel

class OrderItem(orderItemEntity: OrderItemEntity): BaseModel<OrderItemView> {

    override fun toView(): OrderItemView {
        return OrderItemView()
    }

}