package com.giant_giraffe.data.sales.order_item

import com.giant_giraffe.data.BaseModel
import com.giant_giraffe.data.sales.order.Order
import java.math.BigDecimal

class OrderItem(
    var id: Int? = null,
    var orderId: Int? = null,
    var quantity: Int? = null,
    var listPrice: BigDecimal? = null,
    var discount: BigDecimal? = null,
    var productId: Int? = null,
): BaseModel<OrderItemView> {

    constructor(orderItemEntity: OrderItemEntity): this(
        orderItemEntity.id.value,
        orderItemEntity.orderId.value,
        orderItemEntity.quantity,
        orderItemEntity.listPrice,
        orderItemEntity.discount,
        orderItemEntity.product?.value,
    )

    override fun toView(): OrderItemView {
        return OrderItemView(
            id,
            orderId,
            quantity,
            listPrice?.toString(),
            discount?.toString(),
            productId,
        )
    }

}