package com.giant_giraffe.services.sales.customer

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.sales.customer.Customer

interface CustomerService {

    fun create(customer: Customer): Customer

    fun getById(customerId: Int): Customer?

    fun getByEmail(email: String): Customer?

    fun find(page: Int, size: Int): PagedData<Customer>

    fun update(customer: Customer): Int

    fun delete(customerId: Int): Boolean

}