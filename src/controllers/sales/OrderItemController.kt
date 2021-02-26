package com.giant_giraffe.controllers.sales

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.sales.order_item.OrderItemConverter
import com.giant_giraffe.services.sales.order_item.OrderItemService
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di
import java.lang.Exception

fun Route.orderItemController() {

    val orderItemService by di().instance<OrderItemService>()

    route("/orderItems") {

        get {
            try {
                val queryParameters = call.request.queryParameters

                val page = queryParameters["page"]?.toInt()
                val size = queryParameters["size"]?.toInt()
                if (page == null || size == null) {
                    throw Exception("")
                }

                val pagingData = orderItemService.getList(
                    page = page,
                    size = size
                )

                call.respondApiResult(
                    result = PageableData(
                        pagingData.data.map { it.toView() },
                        pagingData.pageInfo,
                    )
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

    route("/orderItem") {

        post("/create") {
            try {
                val form = call.receiveParameters()
                val orderItem = OrderItemConverter.parametersToModel(form)

                val createdOrderItem = orderItemService.create(orderItem)

                call.respondApiResult(
                    result = createdOrderItem.toView()
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        get("/{id}") {
            try {
                val orderItemId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val itemView = orderItemService.getById(orderItemId)
                    ?: throw NotFoundException()

                call.respondApiResult(result = itemView.toView())
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/update/{id}") {
            try {
                val form = call.receiveParameters()
                val orderItem = OrderItemConverter.parametersToModel(form)

                val orderItemId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()
                orderItem.id = orderItemId

                val result = orderItemService.update(orderItem)
                call.respondApiResult(result = result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/delete/{id}") {
            try {
                val orderItemId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val result = orderItemService.delete(orderItemId)
                call.respondApiResult(result = result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

}