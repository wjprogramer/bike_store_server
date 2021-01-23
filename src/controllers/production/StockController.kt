package com.giant_giraffe.controllers.production

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.production.stock.StockConverter
import com.giant_giraffe.services.production.stock.StockService
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
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

                val page = queryParameters["page"]?.toInt()
                val size = queryParameters["size"]?.toInt()
                if (page == null || size == null) {
                    throw Exception("")
                }

                val pagingData = stockService.getList(
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

    route("/stock") {

        post("/create") {
            try {
                val form = call.receiveParameters()
                val stockView = StockConverter.parametersToView(form)
                val stock = StockConverter.viewToModel(stockView)

                val createdStock = stockService.create(stock)
                call.respondApiResult(
                    result = createdStock
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        get("/{id}") {
            try {
                val stockId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()

                val stockView = stockService.getById(stockId) ?: throw NotFoundException()
                call.respond(stockView)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/update/{id}") {
            try {
                val form = call.receiveParameters()
                val stockView = StockConverter.parametersToView(form)
                val stock = StockConverter.viewToModel(stockView)

                val stockId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
                stock.id = stockId

                val result = stockService.update(stock)
                call.respond(result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/delete/{id}") {
            try {
                val stockId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()

                val result = stockService.delete(stockId)
                call.respond(result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

}