package com.giant_giraffe.controllers.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.receiveAndToModel
import com.giant_giraffe.data.sales.order_item.OrderItemConverter
import com.giant_giraffe.services.sales.order_item.OrderItemService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Route.orderItemController() {

    val orderItemService by di().instance<OrderItemService>()

    route("/orderItems") {

        get {
            val queryParameters = call.request.queryParameters

            val page = queryParameters["page"]?.toIntOrNull() ?: 0
            val size = queryParameters["size"]?.toIntOrNull() ?: 10
            val orderId = queryParameters["order_id"]?.toIntOrNull()

            val pagingData = orderItemService.find(
                page = page,
                size = size,
                orderId = orderId,
            )

            call.respondApiResult(
                result = PagedData(
                    pagingData.data.map { it.toView() },
                    pagingData.pageInfo,
                )
            )
        }

    }

    route("/orderItem") {

        post("/create") {
            val orderItem = OrderItemConverter.receiveAndToModel(call)

            val createdOrderItem = orderItemService.create(orderItem)

            call.respondApiResult(
                result = createdOrderItem.toView()
            )
        }

    }

}