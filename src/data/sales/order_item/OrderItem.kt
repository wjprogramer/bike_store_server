package com.giant_giraffe.data.sales.order_item

import com.giant_giraffe.data.BaseModel

class OrderItem(orderItemEntity: OrderItemEntity): BaseModel<OrderItemView> {

    var id = orderItemEntity.id.value
    var orderId = orderItemEntity.orderId.value
    var quantity = orderItemEntity.quantity
    var listPrice = orderItemEntity.listPrice
    var discount = orderItemEntity.discount
    var productId = orderItemEntity.productId?.value

    override fun toView(): OrderItemView {
        return OrderItemView(
            id,
            orderId,
            quantity,
            listPrice.toInt(),
            discount.toInt(),
            productId,
        )
    }

}