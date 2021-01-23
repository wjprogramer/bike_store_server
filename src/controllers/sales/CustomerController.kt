package com.giant_giraffe.controllers.sales

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.core.respondApiErrorResult
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.sales.customer.CustomerConverter
import com.giant_giraffe.data.sales.store.StoreConverter
import com.giant_giraffe.exceptions.MyBaseException
import com.giant_giraffe.services.production.product.ProductService
import com.giant_giraffe.services.sales.customer.CustomerService
import com.giant_giraffe.services.sales.store.StoreService
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di
import java.lang.Exception

fun Route.customerController() {

    val customerService by di().instance<CustomerService>()

    route("/customers") {

        get {
            try {
                val queryParameters = call.request.queryParameters

                val page = queryParameters["page"]?.toInt()
                val size = queryParameters["size"]?.toInt()
                if (page == null || size == null) {
                    throw Exception("")
                }

                val pagingData = customerService.getList(
                    page = page,
                    size = size
                )

                call.respondApiResult(
                    result = PageableData(
                        pagingData.data,
                        pagingData.pageInfo,
                    )
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

    route("/customer") {

        post("/create") {
            try {
                val form = call.receiveParameters()
                val customerView = CustomerConverter.parametersToView(form)
                val customer = CustomerConverter.viewToModel(customerView)

                val createdCustomer = customerService.create(customer)
                call.respondApiResult(
                    result = createdCustomer
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        get("/{id}") {
            try {
                val customerId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()

                val customerView = customerService.getById(customerId) ?: throw NotFoundException()
                call.respond(customerView)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/update/{id}") {
            try {
                val form = call.receiveParameters()
                val customerView = CustomerConverter.parametersToView(form)
                val customer = CustomerConverter.viewToModel(customerView)

                val customerId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
                customer.id = customerId

                val result = customerService.update(customer)
                call.respond(result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/delete/{id}") {
            try {
                val customerId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()

                val result = customerService.delete(customerId)
                call.respond(result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

}