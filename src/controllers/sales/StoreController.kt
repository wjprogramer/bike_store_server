package com.giant_giraffe.controllers.sales

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.core.respondApiErrorResult
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.sales.store.StoreConverter
import com.giant_giraffe.data.sales.store.StoreView
import com.giant_giraffe.exceptions.MyBaseException
import com.giant_giraffe.exceptions.UnknownException
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

fun Route.storeController() {

    val storeService by di().instance<StoreService>()

    route("/stores") {

        get {
            try {
                val queryParameters = call.request.queryParameters

                val page = queryParameters["page"]?.toInt()
                val size = queryParameters["size"]?.toInt()
                if (page == null || size == null) {
                    throw Exception("")
                }

                val pagingData = storeService.getList(
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

    route("/store") {

        post("/create") {
            try {
                val form = call.receiveParameters()
                val storeView = StoreConverter.parametersToView(form)
                val store = StoreConverter.viewToModel(storeView)

                val createdStore = storeService.create(store)
                call.respondApiResult(
                    result = createdStore
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        get("/{id}") {
            try {
                val storeId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()

                val storeView = storeService.getById(storeId) ?: throw NotFoundException()
                call.respond(storeView)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/update/{id}") {
            try {
                val form = call.receiveParameters()
                val storeView = StoreConverter.parametersToView(form)
                val store = StoreConverter.viewToModel(storeView)

                val storeId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
                store.id = storeId

                val result = storeService.update(store)
                call.respond(result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/delete/{id}") {
            try {
                val storeId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()

                val result = storeService.delete(storeId)
                call.respond(result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

}