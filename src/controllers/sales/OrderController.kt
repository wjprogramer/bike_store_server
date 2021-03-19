package com.giant_giraffe.controllers.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.receiveAndToModel
import com.giant_giraffe.data.sales.order.OrderConverter
import com.giant_giraffe.data.sales.order.OrderView
import com.giant_giraffe.data.sales.order_item.OrderItemConverter
import com.giant_giraffe.enums.OrderStatus
import com.giant_giraffe.enums.toOrderStatus
import com.giant_giraffe.exceptions.BadRequestException
import com.giant_giraffe.services.sales.order.OrderService
import com.giant_giraffe.services.sales.order_item.OrderItemService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Route.orderController() {

    val orderService by di().instance<OrderService>()
    val orderItemService by di().instance<OrderItemService>()

    route("/orders") {

        get {
            val queryParameters = call.request.queryParameters

            val page        = queryParameters["page"]?.toInt() ?: 0
            val size        = queryParameters["size"]?.toInt() ?: 10
            var orderStatus = queryParameters["order_status"]?.toOrderStatus()
            val customerId  = queryParameters["customer_id"]?.toIntOrNull()
            val storeId     = queryParameters["store_id"]?.toIntOrNull()
            val staffId     = queryParameters["staff_id"]?.toIntOrNull()

            if (orderStatus == OrderStatus.UNKNOWN) {
                orderStatus = null
            }

            val pagingData = orderService.find(
                page = page,
                size = size,
                orderStatus = orderStatus,
                customerId = customerId,
                storeId = storeId,
                staffId = staffId,
            )

            call.respondApiResult(
                result = PagedData(
                    pagingData.data.map { it.toView() },
                    pagingData.pageInfo,
                )
            )
        }

    }

    route("/order") {

        post("/create") {
            val order = OrderConverter.receiveAndToModel(call)

            val createdOrder = orderService.create(order)

            call.respondApiResult(
                result = createdOrder.toView()
            )
        }

        get("/{id}") {
            val orderId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of order")

            val order = orderService.getById(orderId)
                ?: throw NotFoundException()

            call.respondApiResult(result = order.toView())
        }

        get("/{id}/item/{item_id}") {
            val orderId = call.parameters["id"]?.toIntOrNull()
            val orderItemId = call.parameters["item_id"]?.toIntOrNull()

            if (orderId == null || orderItemId == null) {
                throw BadRequestException("No ID of order or order item")
            }

            val itemView = orderItemService.getById(orderId, orderItemId)
                ?: throw NotFoundException()

            call.respondApiResult(result = itemView.toView())
        }

        post("/update/{id}") {
            val order = OrderConverter.receiveAndToModel(call)

            val result = orderService.update(order)
            call.respondApiResult(result = result)
        }

        post("/{id}/item/{item_id}/update") {
            val orderId = call.parameters["id"]?.toIntOrNull()
            val orderItemId = call.parameters["item_id"]?.toIntOrNull()

            if (orderId == null || orderItemId == null) {
                throw BadRequestException("No ID of order or order item")
            }

            val orderItem = OrderItemConverter.receiveAndToModel(call)

            orderItem.id = orderItemId
            orderItem.orderId = orderId

            val result = orderItemService.update(orderItem)
            call.respondApiResult(result = result)
        }

        post("/delete/{id}") {
            val orderId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of order")

            val result = orderService.delete(orderId)
            call.respondApiResult(result = result)
        }

        post("/{id}/item/{item_id}/delete") {
            val orderId = call.parameters["id"]?.toIntOrNull()
            val orderItemId = call.parameters["item_id"]?.toIntOrNull()

            if (orderId == null || orderItemId == null) {
                throw BadRequestException("No ID of order or order item")
            }

            val result = orderItemService.delete(orderId, orderItemId)
            call.respondApiResult(result = result)
        }
    }
        
}