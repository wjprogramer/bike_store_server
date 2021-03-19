package com.giant_giraffe.controllers.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.production.brand.BrandConverter
import com.giant_giraffe.data.production.brand.BrandView
import com.giant_giraffe.data.receiveAndToModel
import com.giant_giraffe.exceptions.BadRequestException
import com.giant_giraffe.services.production.brand.BrandService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Route.brandController() {

    val brandService by di().instance<BrandService>()

    route("/brands") {

        get {
            val queryParameters = call.request.queryParameters

            val page = queryParameters["page"]?.toInt() ?: 0
            val size = queryParameters["size"]?.toInt() ?: 10

            val pagingData = brandService.find(
                page = page,
                size = size
            )

            call.respondApiResult(
                result = PagedData(
                    pagingData.data.map { it.toView() },
                    pagingData.pageInfo,
                )
            )
        }

        get("/all") {
            val brands = brandService.findAll()

            call.respondApiResult(
                result = brands.map { it.toView() }
            )
        }

    }

    route("/brand") {

        post("/create") {
            val brand = BrandConverter.receiveAndToModel(call)

            val createdBrand = brandService.create(brand)

            call.respondApiResult(
                result = createdBrand.toView()
            )
        }

        get("/{id}") {
            val brandId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of brand")

            val brand = brandService.getById(brandId)
                ?: throw NotFoundException()

            call.respondApiResult(result = brand.toView())
        }

        post("/update/{id}") {
            val brand = BrandConverter.receiveAndToModel(call)

            val brandId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of brand")
            brand.id = brandId

            val result = brandService.update(brand)
            call.respondApiResult(result = result)
        }

        post("/delete/{id}") {
            val brandId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of brand")

            val result = brandService.delete(brandId)
            call.respondApiResult(result = result)
        }

    }

}