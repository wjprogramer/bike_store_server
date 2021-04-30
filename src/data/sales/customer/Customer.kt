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
    var avatarName: String? = null,
): BaseModel<CustomerView> {

    constructor(customerEntity: CustomerEntity): this(
            id =            customerEntity.id.value,
            firstName =     customerEntity.firstName,
            lastName =      customerEntity.lastName,
            email =         customerEntity.email,
            password =      customerEntity.password,
            phone =         customerEntity.phone,
            street =        customerEntity.street,
            city =          customerEntity.city,
            state =         customerEntity.state,
            zipCode =       customerEntity.zipCode,
            avatarName =    customerEntity.avatarName,
    )

    override fun toView(): CustomerView {
        return CustomerView(
            id =            id,
            firstName =     firstName,
            lastName =      lastName,
            email =         email,
            phone =         phone,
            street =        street,
            city =          city,
            state =         state,
            zipCode =       zipCode,
            avatarName =    avatarName,
        )
    }

}