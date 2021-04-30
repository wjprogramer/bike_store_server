package com.giant_giraffe.data.sales.customer

import com.giant_giraffe.data.BaseConverter
import io.ktor.http.*

object CustomerConverter: BaseConverter<CustomerEntity, Customer, CustomerView>() {

    override fun parametersToView(parameters: Parameters): CustomerView {
        val result = CustomerView()

        result.id =         parameters["id"]?.toIntOrNull()
        result.firstName =  parameters["first_name"]
        result.lastName =   parameters["last_name"]
        result.email =      parameters["email"]
        result.phone =      parameters["phone"]
        result.street =     parameters["street"]
        result.city =       parameters["city"]
        result.state =      parameters["state"]
        result.zipCode =    parameters["zip_code"]
        result.avatarName = parameters["avatar_name"]

        return result
    }

    override fun viewToModel(view: CustomerView) = Customer(
        id =            view.id,
        firstName =     view.firstName,
        lastName =      view.lastName,
        email =         view.email,
        password =      null,
        phone =         view.phone,
        street =        view.street,
        city =          view.city,
        state =         view.state,
        zipCode =       view.zipCode,
        avatarName =    view.avatarName,
    )

    override fun mapToView(mapping: Map<String, Any?>): CustomerView {
        val view = CustomerView()

        view.id             = mapping.getOrDefault("id", null)?.toString()?.toIntOrNull()
        view.firstName      = mapping.getOrDefault("first_name", null)?.toString()
        view.lastName       = mapping.getOrDefault("last_name", null)?.toString()
        view.email          = mapping.getOrDefault("email", null)?.toString()
        view.phone          = mapping.getOrDefault("phone", null)?.toString()
        view.street         = mapping.getOrDefault("street", null)?.toString()
        view.city           = mapping.getOrDefault("city", null)?.toString()
        view.state          = mapping.getOrDefault("state", null)?.toString()
        view.zipCode        = mapping.getOrDefault("zip_code", null)?.toString()
        view.avatarName     = mapping.getOrDefault("avatar_name", null)?.toString()

        return view
    }

    override fun mapToModel(mapping: Map<String, Any?>): Customer {
        val model = super.mapToModel(mapping)

        model.password = mapping.getOrDefault("password", null)?.toString()

        return model
    }

}