package com.giant_giraffe.data.sales.customer

import com.giant_giraffe.data.BaseModel

class Customer(
    var id: Int? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null,
    var phone: String? = null,
    var street: String? = null,
    var city: String? = null,
    var state: String? = null,
    var zipCode: String? = null,
): BaseModel<CustomerView> {

    constructor(customerEntity: CustomerEntity): this(
        customerEntity.id.value,
        customerEntity.firstName,
        customerEntity.lastName,
        customerEntity.email,
        customerEntity.password,
        customerEntity.phone,
        customerEntity.street,
        customerEntity.city,
        customerEntity.state,
        customerEntity.zipCode,
    )

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