package com.giant_giraffe.controllers.production

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.production.brand.BrandConverter
import com.giant_giraffe.services.production.brand.BrandService
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
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

                val page = queryParameters["page"]?.toInt()
                val size = queryParameters["size"]?.toInt()
                if (page == null || size == null) {
                    throw Exception("")
                }

                val pagingData = brandService.getList(
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

    route("/brand") {

        post("/create") {
            try {
                val form = call.receiveParameters()
                val brandView = BrandConverter.parametersToView(form)
                val brand = BrandConverter.viewToModel(brandView)

                val createdBrand = brandService.create(brand)
                call.respondApiResult(
                    result = createdBrand
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        get("/{id}") {
            try {
                val brandId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()

                val brandView = brandService.getById(brandId) ?: throw NotFoundException()
                call.respond(brandView)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/update/{id}") {
            try {
                val form = call.receiveParameters()
                val brandView = BrandConverter.parametersToView(form)
                val brand = BrandConverter.viewToModel(brandView)

                val brandId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
                brand.id = brandId

                val result = brandService.update(brand)
                call.respond(result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/delete/{id}") {
            try {
                val brandId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()

                val result = brandService.delete(brandId)
                call.respond(result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

}