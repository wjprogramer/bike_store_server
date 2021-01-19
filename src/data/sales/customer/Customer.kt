package com.giant_giraffe.data.sales.customer

import com.giant_giraffe.data.BaseModel

class Customer(customerEntity: CustomerEntity): BaseModel<CustomerView> {

    override fun toView(): CustomerView {
        return CustomerView()
    }

}