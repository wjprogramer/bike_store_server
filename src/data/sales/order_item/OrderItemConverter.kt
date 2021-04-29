package com.giant_giraffe.data.sales.order_item

import com.giant_giraffe.data.BaseConverter
import io.ktor.http.*

object OrderItemConverter: BaseConverter<OrderItemEntity, OrderItem, OrderItemView>() {

    override fun parametersToView(parameters: Parameters): OrderItemView {
        val result = OrderItemView()

        result.id =         parameters["id"]?.toIntOrNull()
        result.orderId =    parameters["order_id"]?.toIntOrNull()
        result.quantity =   parameters["quantity"]?.toIntOrNull()
        result.listPrice =  parameters["list_price"]
        result.discount =   parameters["discount"]
        result.productId =  parameters["product_id"]?.toIntOrNull()

        return result
    }

    override fun viewToModel(view: OrderItemView) = OrderItem(
        id =            view.id,
        orderId =       view.orderId,
        quantity =      view.quantity,
        listPrice =     view.listPrice?.toBigDecimalOrNull(),
        discount =      view.discount?.toBigDecimalOrNull(),
        productId =     view.productId,
    )

    override fun mapToView(mapping: Map<String, Any?>): OrderItemView {
        TODO("Not yet implemented")
    }

}