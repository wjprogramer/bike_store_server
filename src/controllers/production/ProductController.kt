package com.giant_giraffe.controllers.production

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.production.product.ProductConverter
import com.giant_giraffe.services.production.product.ProductService
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di
import java.lang.Exception

fun Route.productController() {

    val productService by di().instance<ProductService>()

    route("/products") {

        get {
            try {
                val queryParameters = call.request.queryParameters

                val page = queryParameters["page"]?.toInt()
                val size = queryParameters["size"]?.toInt()
                if (page == null || size == null) {
                    throw Exception("")
                }

                val pagingData = productService.getList(
                    page = page,
                    size = size
                )

                call.respondApiResult(
                    result = PageableData(
                        pagingData.data.map { it.toView() },
                        pagingData.pageInfo,
                    )
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

    route("/product") {

        post("/create") {
            try {
                val form = call.receiveParameters()
                val product = ProductConverter.parametersToModel(form)

                val createdProduct = productService.create(product)

                call.respondApiResult(
                    result = createdProduct
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        get("/{id}") {
            try {
                val productId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val productView = productService.getById(productId)
                    ?: throw NotFoundException()

                call.respond(productView)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/update/{id}") {
            try {
                val form = call.receiveParameters()
                val product = ProductConverter.parametersToModel(form)

                val productId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()
                product.id = productId

                val result = productService.update(product)
                call.respond(result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/delete/{id}") {
            try {
                val productId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val result = productService.delete(productId)
                call.respond(result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

}