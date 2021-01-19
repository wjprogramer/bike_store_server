package com.giant_giraffe.data.sales.order

import com.giant_giraffe.data.BaseModel

class Order(orderEntity: OrderEntity): BaseModel<OrderView> {

    override fun toView(): OrderView {
        return OrderView()
    }

}