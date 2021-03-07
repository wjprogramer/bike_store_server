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

}