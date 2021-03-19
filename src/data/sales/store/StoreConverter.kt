package com.giant_giraffe.data.sales.store

import com.giant_giraffe.data.BaseConverter
import io.ktor.http.*

object StoreConverter: BaseConverter<StoreEntity, Store, StoreView> {

    override fun parametersToView(parameters: Parameters): StoreView {
        val result = StoreView()

        result.id =         parameters["id"]?.toInt()
        result.storeName =  parameters["store_name"]
        result.phone =      parameters["phone"]
        result.email =      parameters["email"]
        result.street =     parameters["street"]
        result.city =       parameters["city"]
        result.state =      parameters["state"]
        result.zipCode =    parameters["zip_code"]

        return result
    }

    override fun viewToModel(view: StoreView) = Store(
        id =            view.id,
        storeName =     view.storeName,
        phone =         view.phone,
        email =         view.email,
        street =        view.street,
        city =          view.city,
        state =         view.state,
        zipCode =       view.zipCode,
    )

    override fun mapToView(mapping: Map<String, Any?>): StoreView {
        val view = StoreView()

        view.id             = mapping["id"]?.toString()?.toIntOrNull()
        view.storeName      = mapping["store_name"]?.toString()
        view.phone          = mapping["phone"]?.toString()
        view.email          = mapping["email"]?.toString()
        view.street         = mapping["street"]?.toString()
        view.city           = mapping["city"]?.toString()
        view.state          = mapping["state"]?.toString()
        view.zipCode        = mapping["zip_code"]?.toString()

        return view
    }

}