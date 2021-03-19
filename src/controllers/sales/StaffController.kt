package com.giant_giraffe.controllers.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.receiveAndToModel
import com.giant_giraffe.data.sales.staff.StaffConverter
import com.giant_giraffe.exceptions.BadRequestException
import com.giant_giraffe.services.sales.staff.StaffService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Route.staffController() {

    val staffService by di().instance<StaffService>()

    route("/staffs") {

        get {
            val queryParameters = call.request.queryParameters

            val page = queryParameters["page"]?.toIntOrNull() ?: 0
            val size = queryParameters["size"]?.toIntOrNull() ?: 10
            val storeId = queryParameters["store_id"]?.toIntOrNull()

            val pagingData = staffService.find(
                page = page,
                size = size,
                storeId = storeId,
            )

            call.respondApiResult(
                result = PagedData(
                    pagingData.data.map { it.toView() },
                    pagingData.pageInfo,
                )
            )
        }

    }

    route("/staff") {

        post("/create") {
            val body = call.receiveOrNull<Map<String, String>>()
                ?: throw BadRequestException()
            val staff = StaffConverter.mapToModel(body)

            val createdStaff = staffService.create(staff)

            call.respondApiResult(
                result = createdStaff.toView()
            )
        }

        get("/{id}") {
            val staffId = call.parameters["id"]?.toIntOrNull()
                ?: throw NotFoundException()

            val staff = staffService.getById(staffId)
                ?: throw NotFoundException()

            call.respondApiResult(result = staff.toView())
        }

        post("/update/{id}") {
            val staff = StaffConverter.receiveAndToModel(call)

            val staffId = call.parameters["id"]?.toIntOrNull()
                ?: throw NotFoundException()
            staff.id = staffId

            val result = staffService.update(staff)
            call.respondApiResult(result = result)
        }

        post("/delete/{id}") {
            val staffId = call.parameters["id"]?.toIntOrNull()
                ?: throw NotFoundException()

            val result = staffService.delete(staffId)
            call.respondApiResult(result = result)
        }

    }

}
