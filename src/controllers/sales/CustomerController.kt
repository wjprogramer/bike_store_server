package com.giant_giraffe.controllers.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.sales.customer.CustomerConverter
import com.giant_giraffe.services.sales.customer.CustomerService
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
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

                val page = queryParameters["page"]?.toInt() ?: 0
                val size = queryParameters["size"]?.toInt() ?: 10
                val keyword = queryParameters["keyword"]

                val pagingData = customerService.find(
                    page = page,
                    size = size,
                    keyword = keyword,
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

    route("/customer") {

        post("/create") {
            try {
                val form = call.receiveParameters()
                val customer = CustomerConverter.parametersToModel(form)
                customer.password = form["password"]

                val createdCustomer = customerService.create(customer)

                call.respondApiResult(
                    result = createdCustomer.toView()
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        get("/{id}") {
            try {
                val customerId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val customer = customerService.getById(customerId)
                    ?: throw NotFoundException()

                call.respondApiResult(
                    result = customer.toView()
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/update/{id}") {
            try {
                val form = call.receiveParameters()
                val customer = CustomerConverter.parametersToModel(form)

                val customerId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()
                customer.id = customerId

                val result = customerService.update(customer)
                call.respondApiResult(result = result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/delete/{id}") {
            try {
                val customerId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val result = customerService.delete(customerId)
                call.respondApiResult(result = result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

}