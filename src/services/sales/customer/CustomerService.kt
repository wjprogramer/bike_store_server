package com.giant_giraffe.services.sales.customer

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.sales.customer.Customer
import com.giant_giraffe.data.sales.customer.CustomerView
import com.giant_giraffe.data.sales.staff.StaffView

interface CustomerService {

    fun create(customer: Customer): Customer

    fun getById(customerId: Int): Customer?

    fun getList(page: Int, size: Int): PageableData<Customer>

    fun update(customer: Customer): Int

    fun delete(customerId: Int): Boolean

}