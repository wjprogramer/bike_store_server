package com.giant_giraffe.data.sales.order_item

import com.giant_giraffe.data.BaseModel
import com.giant_giraffe.data.production.product.Product
import java.math.BigDecimal

class OrderItem(
    var id: Int? = null,
    var orderId: Int? = null,
    var quantity: Int? = null,
    var listPrice: BigDecimal? = null,
    var discount: BigDecimal? = null,
    var productId: Int? = null,

    var product: Product? = null
): BaseModel<OrderItemView> {

    constructor(orderItemEntity: OrderItemEntity): this(
            id =            orderItemEntity.id.value,
            orderId =       orderItemEntity.orderId.value,
            quantity =      orderItemEntity.quantity,
            listPrice =     orderItemEntity.listPrice,
            discount =      orderItemEntity.discount,
            productId =     orderItemEntity.productId?.value,
    )

    override fun toView(): OrderItemView {
        return OrderItemView(
            id =            id,
            orderId =       orderId,
            quantity =      quantity,
            listPrice =     listPrice?.toString(),
            discount =      discount?.toString(),
            productId =     productId,
            product =       product?.toView(),
        )
    }

}