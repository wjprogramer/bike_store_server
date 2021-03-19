package com.giant_giraffe.controllers.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.receiveAndToModel
import com.giant_giraffe.data.sales.customer.CustomerConverter
import com.giant_giraffe.exceptions.BadRequestException
import com.giant_giraffe.services.sales.customer.CustomerService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Route.customerController() {

    val customerService by di().instance<CustomerService>()

    route("/customers") {

        get {
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
        }

    }

    route("/customer") {

        post("/create") {
            val body = call.receiveOrNull<Map<String, String>>()
                ?: throw BadRequestException()
            val customer = CustomerConverter.mapToModel(body)

            val createdCustomer = customerService.create(customer)

            call.respondApiResult(
                result = createdCustomer.toView()
            )
        }

        get("/{id}") {
            val customerId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of customer")

            val customer = customerService.getById(customerId)
                ?: throw NotFoundException()

            call.respondApiResult(
                result = customer.toView()
            )
        }

        post("/update/{id}") {
            val customer = CustomerConverter.receiveAndToModel(call)

            val customerId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of customer")
            customer.id = customerId

            val result = customerService.update(customer)
            call.respondApiResult(result = result)
        }

        post("/delete/{id}") {
            val customerId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of customer")

            val result = customerService.delete(customerId)
            call.respondApiResult(result = result)
        }

    }

}