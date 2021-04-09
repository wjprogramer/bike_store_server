package com.giant_giraffe.controllers.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.production.product.ProductConverter
import com.giant_giraffe.data.receiveAndToModel
import com.giant_giraffe.enums.UserType
import com.giant_giraffe.exceptions.BadRequestException
import com.giant_giraffe.extensions.authPost
import com.giant_giraffe.extensions.user
import com.giant_giraffe.services.production.product.ProductService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di
import java.lang.Exception

fun Route.productController() {

    val productService by di().instance<ProductService>()

    route("/products") {

        get {
            val queryParameters = call.request.queryParameters

            val page = queryParameters["page"]?.toInt() ?: 0
            val size = queryParameters["size"]?.toInt() ?: 10
            val keyword = queryParameters["keyword"]
            val modelYear = queryParameters["model_year"]?.toIntOrNull()
            val brandId = queryParameters["brand_id"]?.toIntOrNull()
            val categoryId = queryParameters["category_id"]?.toIntOrNull()
            val minListPrice = queryParameters["min_list_price"]?.toIntOrNull()
            val maxListPrice = queryParameters["max_list_price"]?.toIntOrNull()

            val pagingData = productService.find(
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
        }

    }

    route("/product") {

        authPost("/create") {
            val product = ProductConverter.receiveAndToModel(call)

            val user = call.user
            if (user == null || user.type != UserType.STAFF) {
                throw Exception()
            }

            val createdProduct = productService.create(product)

            call.respondApiResult(
                result = createdProduct.toView()
            )
        }

        get("/{id}") {
            val productId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of product")

            val product = productService.getById(productId)
                ?: throw NotFoundException()

            call.respondApiResult(result = product.toView())
        }

        post("/update/{id}") {
            val product = ProductConverter.receiveAndToModel(call)

            val productId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of product")
            product.id = productId

            val result = productService.update(product)
            call.respondApiResult(result = result)
        }

        post("/delete/{id}") {
            val productId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of product")

            val result = productService.delete(productId)
            call.respondApiResult(result = result)
        }

    }

}