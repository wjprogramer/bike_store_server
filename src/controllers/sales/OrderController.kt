package com.giant_giraffe.controllers.sales

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.sales.order.OrderConverter
import com.giant_giraffe.services.sales.order.OrderService
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di
import java.lang.Exception

fun Route.orderController() {

    val orderService by di().instance<OrderService>()

    route("/orders") {

        get {
            try {
                val queryParameters = call.request.queryParameters

                val page = queryParameters["page"]?.toInt()
                val size = queryParameters["size"]?.toInt()
                if (page == null || size == null) {
                    throw Exception("")
                }

                val pagingData = orderService.getList(
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

    route("/order") {

        post("/create") {
            try {
                val form = call.receiveParameters()
                val order = OrderConverter.parametersToModel(form)

                val createdOrder = orderService.create(order)

                call.respondApiResult(
                    result = createdOrder.toView()
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        get("/{id}") {
            try {
                val orderId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val order = orderService.getById(orderId)
                    ?: throw NotFoundException()

                call.respondApiResult(result = order.toView())
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/update/{id}") {
            try {
                val form = call.receiveParameters()
                val order = OrderConverter.parametersToModel(form)

                val orderId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()
                order.id = orderId

                val result = orderService.update(order)
                call.respondApiResult(result = result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/delete/{id}") {
            try {
                val orderId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val result = orderService.delete(orderId)
                call.respondApiResult(result = result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }
    }
        
}