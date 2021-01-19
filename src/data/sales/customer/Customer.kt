package com.giant_giraffe.data.sales.customer

import com.giant_giraffe.data.BaseModel

class Customer(customerEntity: CustomerEntity): BaseModel<CustomerView> {

    var id = customerEntity.id.value
    var firstName = customerEntity.firstName
    var lastName = customerEntity.lastName
    var email = customerEntity.email
    var phone = customerEntity.phone
    var street = customerEntity.street
    var city = customerEntity.city
    var state = customerEntity.state
    var zipCode = customerEntity.zipCode

    override fun toView(): CustomerView {
        return CustomerView(
            id,
            firstName,
            lastName,
            email,
            phone,
            street,
            city,
            state,
            zipCode,
        )
    }

}