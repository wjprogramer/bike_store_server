package com.giant_giraffe.controllers.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.sales.order_item.OrderItemConverter
import com.giant_giraffe.services.sales.order_item.OrderItemService
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
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

                val page = queryParameters["page"]?.toInt() ?: 0
                val size = queryParameters["size"]?.toInt() ?: 10

                val pagingData = orderItemService.find(
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

    }

}