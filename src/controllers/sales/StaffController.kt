package com.giant_giraffe.controllers.sales

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.core.respondApiResult
import com.giant_giraffe.data.sales.staff.StaffConverter
import com.giant_giraffe.services.sales.staff.StaffService
import com.giant_giraffe.utility.ApiUtility
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di
import java.lang.Exception

fun Route.staffController() {

    val staffService by di().instance<StaffService>()

    route("/staffs") {

        get {
            try {
                val queryParameters = call.request.queryParameters

                val page = queryParameters["page"]?.toInt()
                val size = queryParameters["size"]?.toInt()
                if (page == null || size == null) {
                    throw Exception("")
                }

                val pagingData = staffService.getList(
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

    route("/staff") {

        post("/create") {
            try {
                val form = call.receiveParameters()
                val staff = StaffConverter.parametersToModel(form)

                val createdStaff = staffService.create(staff)

                call.respondApiResult(
                    result = createdStaff.toView()
                )
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        get("/{id}") {
            try {
                val staffId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val staff = staffService.getById(staffId)
                    ?: throw NotFoundException()

                call.respond(staff.toView())
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/update/{id}") {
            try {
                val form = call.receiveParameters()
                val staff = StaffConverter.parametersToModel(form)

                val staffId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()
                staff.id = staffId

                val result = staffService.update(staff)
                call.respond(result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

        post("/delete/{id}") {
            try {
                val staffId = call.parameters["id"]?.toIntOrNull()
                    ?: throw NotFoundException()

                val result = staffService.delete(staffId)
                call.respond(result)
            } catch (e: Exception) {
                ApiUtility.handleError(e, call)
            }
        }

    }

}