package com.giant_giraffe.data.sales.order

import com.giant_giraffe.data.BaseConverter
import com.giant_giraffe.data.sales.order_item.OrderItemConverter
import com.giant_giraffe.enums.toOrderStatus
import com.giant_giraffe.utility.DateTimeUtility
import io.ktor.http.*

object OrderConverter: BaseConverter<OrderEntity, Order, OrderView>() {

    override fun parametersToView(parameters: Parameters): OrderView {
        val result = OrderView()

        result.id =             parameters["id"]?.toIntOrNull()
        result.orderStatus =    parameters["order_status"]
        result.orderDate =      parameters["order_date"]
        result.requiredDate =   parameters["required_date"]
        result.shippedDate =    parameters["shipped_date"]
        result.customerId =     parameters["customer_id"]?.toIntOrNull()
        result.storeId =        parameters["store_id"]?.toIntOrNull()
        result.staffId =        parameters["staff_id"]?.toIntOrNull()

        return result
    }

    override fun viewToModel(view: OrderView) = Order(
        id =            view.id,
        orderStatus =   view.orderStatus?.toOrderStatus(),
        orderDate =     DateTimeUtility.tryParse(view.orderDate),
        requiredDate =  DateTimeUtility.tryParse(view.requiredDate),
        shippedDate =   DateTimeUtility.tryParse(view.shippedDate),
        customerId =    view.customerId,
        storeId =       view.storeId,
        staffId =       view.staffId,
        orderItems =    view.orderItems?.map { OrderItemConverter.viewToModel(it) }
    )

    override fun mapToView(mapping: Map<String, Any?>): OrderView {
        TODO("Not yet implemented")
    }

}