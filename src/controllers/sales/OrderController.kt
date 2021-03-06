package com.giant_giraffe.controllers.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.sales.order.OrderConverter
import com.giant_giraffe.exceptions.UnknownException
import com.giant_giraffe.services.sales.order.OrderService
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
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

                val page = queryParameters["page"]?.toInt() ?: 0
                val size = queryParameters["size"]?.toInt() ?: 10

                val pagingData = orderService.getList(
                    page = page,
                    size = size
                )

                call.respondApiResult(
                    result = PagedData(
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
                    ?: throw UnknownException()

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
                    ?: throw UnknownException()

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