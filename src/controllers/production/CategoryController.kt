package com.giant_giraffe.controllers.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.production.category.CategoryConverter
import com.giant_giraffe.data.receiveAndToModel
import com.giant_giraffe.exceptions.BadRequestException
import com.giant_giraffe.services.production.category.CategoryService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Route.categoryController() {

    val categoryService by di().instance<CategoryService>()

    route("/categories") {

        get {
            val queryParameters = call.request.queryParameters

            val page = queryParameters["page"]?.toInt() ?: 0
            val size = queryParameters["size"]?.toInt() ?: 10

            val pagingData = categoryService.find(
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
            val categories = categoryService.findAll()

            call.respondApiResult(
                result = categories.map { it.toView() }
            )
        }

    }

    route("/category") {

        post("/create") {
            val category = CategoryConverter.receiveAndToModel(call)

            val createdCategory = categoryService.create(category)

            call.respondApiResult(
                result = createdCategory.toView()
            )
        }

        get("/{id}") {
            val categoryId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of category")

            val category = categoryService.getById(categoryId)
                ?: throw NotFoundException()

            call.respondApiResult(result = category.toView())
        }

        post("/update/{id}") {
            val category = CategoryConverter.receiveAndToModel(call)

            val categoryId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of category")
            category.id = categoryId

            val result = categoryService.update(category)
            call.respondApiResult(result = result)
        }

        post("/delete/{id}") {
            val categoryId = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("No ID of category")

            val result = categoryService.delete(categoryId)
            call.respondApiResult(result = result)
        }

    }

}