package com.giant_giraffe.controllers.production

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.production.category.CategoryConverter
import com.giant_giraffe.services.production.category.CategoryService
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di
import java.lang.Exception

fun Route.categoryController() {

    val categoryService by di().instance<CategoryService>()

    route("/categories") {

        get {
            try {
                val queryParameters = call.request.queryParameters

                val page = queryParameters["page"]?.toInt()
                val size = queryParameters["size"]?.toInt()
                if (page == null || size == null) {
                    throw Exception("")
                }

                val pagingData = categoryService.getList(
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

    route("/category") {

        post("/create") {
            try {
                val form = call.receiveParameters()
                val category = CategoryConverter.parametersToModel(form)

                val createdCategory = categoryService.create(category)

                call.respondApiResult(
                    result = createdCategory.toView()
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        get("/{id}") {
            try {
                val categoryId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val category = categoryService.getById(categoryId)
                    ?: throw NotFoundException()

                call.respond(category.toView())
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/update/{id}") {
            try {
                val form = call.receiveParameters()
                val category = CategoryConverter.parametersToModel(form)

                val categoryId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()
                category.id = categoryId

                val result = categoryService.update(category)
                call.respond(result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/delete/{id}") {
            try {
                val categoryId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val result = categoryService.delete(categoryId)
                call.respond(result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

}