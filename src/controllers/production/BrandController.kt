package com.giant_giraffe.controllers.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.production.brand.BrandConverter
import com.giant_giraffe.exceptions.UnknownException
import com.giant_giraffe.services.production.brand.BrandService
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di
import java.lang.Exception

fun Route.brandController() {

    val brandService by di().instance<BrandService>()

    route("/brands") {

        get {
            try {
                val queryParameters = call.request.queryParameters

                val page = queryParameters["page"]?.toInt() ?: 0
                val size = queryParameters["size"]?.toInt() ?: 10

                val pagingData = brandService.getList(
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

    route("/brand") {

        post("/create") {
            try {
                val form = call.receiveParameters()
                val brand = BrandConverter.parametersToModel(form) ?: throw UnknownException()

                val createdBrand = brandService.create(brand)

                call.respondApiResult(
                    result = createdBrand.toView()
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        get("/{id}") {
            try {
                val brandId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val brand = brandService.getById(brandId)
                    ?: throw NotFoundException()

                call.respondApiResult(result = brand.toView())
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/update/{id}") {
            try {
                val form = call.receiveParameters()
                val brand = BrandConverter.parametersToModel(form)
                    ?: throw UnknownException()

                val brandId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()
                brand.id = brandId

                val result = brandService.update(brand)
                call.respondApiResult(result = result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/delete/{id}") {
            try {
                val brandId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val result = brandService.delete(brandId)
                call.respondApiResult(result = result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

}