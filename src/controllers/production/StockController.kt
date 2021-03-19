package com.giant_giraffe.controllers.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.production.stock.StockConverter
import com.giant_giraffe.data.receiveAndToModel
import com.giant_giraffe.exceptions.BadRequestException
import com.giant_giraffe.services.production.stock.StockService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Route.stockController() {

    val stockService by di().instance<StockService>()

    route("/stocks") {

        get {
            val queryParameters = call.request.queryParameters

            val page = queryParameters["page"]?.toInt() ?: 0
            val size = queryParameters["size"]?.toInt() ?: 10
            val storeId = queryParameters["store_id"]?.toIntOrNull()
            val productId = queryParameters["product_id"]?.toIntOrNull()

            val pagingData = stockService.find(
                page = page,
                size = size,
                storeId = storeId,
                productId = productId,
            )

            call.respondApiResult(
                result = PagedData(
                    pagingData.data.map { it.toView() },
                    pagingData.pageInfo,
                )
            )
        }

    }

    route("/stock") {

        post("/create") {
            val stock = StockConverter.receiveAndToModel(call)

            val createdStock = stockService.create(stock)

            call.respondApiResult(
                result = createdStock.toView()
            )
        }

        get("/{id}") {
            val stockId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of stock")

            val stock = stockService.getById(stockId)
                ?: throw NotFoundException()

            call.respondApiResult(result = stock.toView())
        }

        post("/update/{id}") {
            val stock = StockConverter.receiveAndToModel(call)

            val stockId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of stock")
            stock.id = stockId

            val result = stockService.update(stock)
            call.respondApiResult(result = result)
        }

        post("/delete/{id}") {
            val stockId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of stock")

            val result = stockService.delete(stockId)
            call.respondApiResult(result = result)
        }

    }

}