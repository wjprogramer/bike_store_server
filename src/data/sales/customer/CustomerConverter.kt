package com.giant_giraffe.data.sales.customer

import com.giant_giraffe.data.BaseConverter
import io.ktor.http.*

object CustomerConverter: BaseConverter<CustomerEntity, Customer, CustomerView> {

    override fun parametersToView(parameters: Parameters): CustomerView {
        val result = CustomerView()

        result.id = parameters["id"]?.toIntOrNull()
        result.firstName = parameters["first_name"]
        result.lastName = parameters["last_name"]
        result.email = parameters["email"]
        result.phone = parameters["phone"]
        result.street = parameters["street"]
        result.city = parameters["city"]
        result.state = parameters["state"]
        result.zipCode = parameters["zip_code"]

        return result
    }

    override fun viewToModel(view: CustomerView) = Customer(
        view.id,
        view.firstName,
        view.lastName,
        view.email,
        view.phone,
        view.street,
        view.city,
        view.state,
        view.zipCode,
    )

}