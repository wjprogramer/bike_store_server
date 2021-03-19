package com.giant_giraffe.data.sales.staff

import com.giant_giraffe.data.BaseConverter
import com.giant_giraffe.data.sales.store.StoreConverter
import io.ktor.http.*

object StaffConverter: BaseConverter<StaffEntity, Staff, StaffView> {

    override fun parametersToView(parameters: Parameters): StaffView {
        val result = StaffView()

        result.id           = parameters["id"]?.toIntOrNull()
        result.firstName    = parameters["first_name"]
        result.lastName     = parameters["last_name"]
        result.email        = parameters["email"]
        result.phone        = parameters["phone"]
        result.active       = parameters["active"]?.toIntOrNull()
        result.storeId      = parameters["store_id"]?.toIntOrNull()
        result.managerId    = parameters["manager_id"]?.toIntOrNull()

        return result
    }

    override fun viewToModel(view: StaffView) = Staff(
        id =            view.id,
        firstName =     view.firstName,
        lastName =      view.lastName,
        email =         view.email,
        password =      null,
        phone =         view.phone,
        active =        view.active,
        storeId =       view.storeId,
        managerId =     view.managerId,
    )

    override fun mapToView(mapping: Map<String, Any?>): StaffView {
        val view = StaffView()

        view.id             = mapping["id"]?.toString()?.toIntOrNull()
        view.firstName      = mapping["first_name"]?.toString()
        view.lastName       = mapping["last_name"]?.toString()
        view.email          = mapping["email"]?.toString()
        view.phone          = mapping["phone"]?.toString()
        view.active         = mapping["active"]?.toString()?.toDoubleOrNull()?.toInt()
        view.storeId        = mapping["store_id"]?.toString()?.toDoubleOrNull()?.toInt()
        view.managerId      = mapping["manager_id"]?.toString()?.toDoubleOrNull()?.toInt()
        view.store          = mapping["store"]?.let { value ->
            if (value is Map<*, *>) {
                value
                    .map { it.key.toString() to it.value }
                    .toMap()
                    .let { StoreConverter.mapToView(it) }
            } else {
                null
            }
        }

        return view
    }

    override fun mapToModel(mapping: Map<String, Any?>): Staff {
        val model = super.mapToModel(mapping)

        model.password = mapping.getOrDefault("password", null)?.toString()

        return model
    }

}