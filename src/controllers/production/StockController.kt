package com.giant_giraffe.controllers.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.production.stock.StockConverter
import com.giant_giraffe.exceptions.UnknownException
import com.giant_giraffe.services.production.stock.StockService
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di
import java.lang.Exception

fun Route.stockController() {

    val stockService by di().instance<StockService>()

    route("/stocks") {

        get {
            try {
                val queryParameters = call.request.queryParameters

                val page = queryParameters["page"]?.toInt() ?: 0
                val size = queryParameters["size"]?.toInt() ?: 10

                val pagingData = stockService.getList(
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

    route("/stock") {

        post("/create") {
            try {
                val form = call.receiveParameters()
                val stock = StockConverter.parametersToModel(form)
                    ?: throw UnknownException()

                val createdStock = stockService.create(stock)

                call.respondApiResult(
                    result = createdStock.toView()
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        get("/{id}") {
            try {
                val stockId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val stock = stockService.getById(stockId)
                    ?: throw NotFoundException()

                call.respondApiResult(result = stock.toView())
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/update/{id}") {
            try {
                val form = call.receiveParameters()
                val stock = StockConverter.parametersToModel(form)
                    ?: throw UnknownException()

                val stockId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()
                stock.id = stockId

                val result = stockService.update(stock)
                call.respondApiResult(result = result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/delete/{id}") {
            try {
                val stockId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val result = stockService.delete(stockId)
                call.respondApiResult(result = result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

}