package com.giant_giraffe.data.sales.staff

import com.giant_giraffe.data.BaseConverter
import io.ktor.http.*

object StaffConverter: BaseConverter<StaffEntity, Staff, StaffView> {

    override fun parametersToView(parameters: Parameters): StaffView {
        val result = StaffView()

        result.id = parameters["id"]?.toIntOrNull()
        result.firstName = parameters["first_name"]
        result.lastName = parameters["last_name"]
        result.email = parameters["email"]
        result.phone = parameters["phone"]
        result.active = parameters["active"]?.toIntOrNull()
        result.storeId = parameters["store_id"]?.toIntOrNull()
        result.managerId = parameters["manager_id"]?.toIntOrNull()

        return result
    }

    override fun viewToModel(view: StaffView) = Staff(
        view.id,
        view.firstName,
        view.lastName,
        view.email,
        view.phone,
        view.active,
        view.storeId,
        view.managerId,
    )

}