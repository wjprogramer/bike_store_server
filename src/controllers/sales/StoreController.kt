package com.giant_giraffe.controllers.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.sales.store.StoreConverter
import com.giant_giraffe.services.sales.store.StoreService
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
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

                val page = queryParameters["page"]?.toInt() ?: 0
                val size = queryParameters["size"]?.toInt() ?: 10

                val pagingData = storeService.find(
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

        get("/all") {
            try {
                val stores = storeService.findAll()

                call.respondApiResult(result = stores.map { it.toView() })
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

    route("/store") {

        post("/create") {
            try {
                val form = call.receiveParameters()
                val store = StoreConverter.parametersToModel(form)

                val createdStore = storeService.create(store)

                call.respondApiResult(
                    result = createdStore.toView()
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        get("/{id}") {
            try {
                val storeId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val store = storeService.getById(storeId)
                    ?: throw NotFoundException()

                call.respondApiResult(result = store.toView())
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/update/{id}") {
            try {
                val form = call.receiveParameters()
                val store = StoreConverter.parametersToModel(form)

                val storeId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()
                store.id = storeId

                val result = storeService.update(store)
                call.respondApiResult(result = result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/delete/{id}") {
            try {
                val storeId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val result = storeService.delete(storeId)
                call.respondApiResult(result = result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

}