package com.giant_giraffe.data.sales.order

import com.giant_giraffe.data.BaseModel
import com.giant_giraffe.data.sales.order_item.OrderItem
import com.giant_giraffe.enums.OrderStatus
import com.giant_giraffe.utility.DateTimeUtility
import org.jetbrains.exposed.sql.SizedCollection
import org.joda.time.DateTime

class Order(
    var id: Int? = null,
    var orderStatus: OrderStatus? = null,
    var orderDate: DateTime? = null,
    var requiredDate: DateTime? = null,
    var shippedDate: DateTime? = null,
    var customerId: Int? = null,
    var storeId: Int? = null,
    var staffId: Int? = null,
    var orderItems: List<OrderItem>? = null,
): BaseModel<OrderView> {

    constructor(
        orderEntity: OrderEntity,
        orderItems: List<OrderItem>? = null,
    ): this(
        orderEntity.id.value,
        orderEntity.orderStatus,
        orderEntity.orderDate,
        orderEntity.requiredDate,
        orderEntity.shippedDate,
        orderEntity.customerId?.value,
        orderEntity.storeId.value,
        orderEntity.staffId.value,
        orderItems,
    )

    override fun toView(): OrderView {
        return OrderView(
            id,
            orderStatus?.statusName,
            DateTimeUtility.convertToString(orderDate),
            DateTimeUtility.convertToString(requiredDate),
            DateTimeUtility.convertToString(shippedDate),
            customerId,
            storeId,
            staffId,
            orderItems?.map { it.toView() }
        )
    }

}