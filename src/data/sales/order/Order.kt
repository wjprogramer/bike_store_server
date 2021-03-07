package com.giant_giraffe.data.sales.order

import com.giant_giraffe.data.BaseModel
import com.giant_giraffe.data.sales.order_item.OrderItem
import com.giant_giraffe.enums.OrderStatus
import com.giant_giraffe.utility.DateTimeUtility
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
            id =                orderEntity.id.value,
            orderStatus =       orderEntity.orderStatus,
            orderDate =         orderEntity.orderDate,
            requiredDate =      orderEntity.requiredDate,
            shippedDate =       orderEntity.shippedDate,
            customerId =        orderEntity.customerId?.value,
            storeId =           orderEntity.storeId.value,
            staffId =           orderEntity.staffId.value,
            orderItems =        orderItems,
    )

    override fun toView(): OrderView {
        return OrderView(
            id =                id,
            orderStatus =       orderStatus?.statusName,
            orderDate =         DateTimeUtility.convertToString(orderDate),
            requiredDate =      DateTimeUtility.convertToString(requiredDate),
            shippedDate =       DateTimeUtility.convertToString(shippedDate),
            customerId =        customerId,
            storeId =           storeId,
            staffId =           staffId,
            orderItems =        orderItems?.map { it.toView() }
        )
    }

}