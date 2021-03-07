package com.giant_giraffe.controllers.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.production.product.ProductConverter
import com.giant_giraffe.enums.UserType
import com.giant_giraffe.services.production.product.ProductService
import com.giant_giraffe.utility.ApiUtility
import com.giant_giraffe.utils.authPost
import com.giant_giraffe.utils.user
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
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

                val page = queryParameters["page"]?.toInt() ?: 0
                val size = queryParameters["size"]?.toInt() ?: 10
                val keyword = queryParameters["keyword"]
                val modelYear = queryParameters["model_year"]?.toIntOrNull()
                val brandId = queryParameters["brand_id"]?.toIntOrNull()
                val categoryId = queryParameters["category_id"]?.toIntOrNull()
                val minListPrice = queryParameters["min_list_price"]?.toIntOrNull()
                val maxListPrice = queryParameters["max_list_price"]?.toIntOrNull()

                val pagingData = productService.getList(
                    page = page,
                    size = size,
                    keyword = keyword,
                    modelYear = modelYear,
                    brandId = brandId,
                    categoryId = categoryId,
                    minListPrice = minListPrice,
                    maxListPrice = maxListPrice,
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

    route("/product") {

        authPost("/create") {
            try {
                val form = call.receiveParameters()
                val product = ProductConverter.parametersToModel(form)

                val user = call.user
                if (user == null || user.type != UserType.STAFF) {
                    throw Exception()
                }

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

                call.respondApiResult(result = productView)
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
                call.respondApiResult(result = result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/delete/{id}") {
            try {
                val productId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val result = productService.delete(productId)
                call.respondApiResult(result = result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

}