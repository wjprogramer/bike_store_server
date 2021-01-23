package com.giant_giraffe.data.sales.order_item

import com.giant_giraffe.data.BaseConverter
import io.ktor.http.*

object OrderItemConverter: BaseConverter<OrderItemEntity, OrderItem, OrderItemView> {

    override fun parametersToView(parameters: Parameters): OrderItemView {
        val result = OrderItemView()

        result.id = parameters["id"]?.toIntOrNull()
        result.orderId = parameters["order_id"]?.toIntOrNull()
        result.quantity = parameters["quantity"]?.toIntOrNull()
        result.listPrice = parameters["list_price"]
        result.discount = parameters["discount"]
        result.productId = parameters["product_id"]?.toIntOrNull()

        return result
    }

    override fun viewToModel(view: OrderItemView) = OrderItem(
        view.id,
        view.orderId,
        view.quantity,
        view.listPrice?.toBigDecimalOrNull(),
        view.discount?.toBigDecimalOrNull(),
        view.productId,
    )

}