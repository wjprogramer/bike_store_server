package com.giant_giraffe.services.sales.customer

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.dao.sales.CustomerDao
import com.giant_giraffe.data.sales.customer.Customer
import com.giant_giraffe.data.sales.customer.CustomerEntity
import com.giant_giraffe.data.sales.customer.CustomerTable
import com.giant_giraffe.data.sales.customer.CustomerView
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

class CustomerServiceImpl: CustomerService {

    private val dao = CustomerDao

    override fun create(customer: Customer): Customer {
        if (
            customer.firstName == null ||
            customer.lastName == null ||
            customer.email == null
        ) {
            throw Exception("")
        }

        return dao.create(customer)
    }

    override fun getById(customerId: Int): Customer? {
        return dao.getById(customerId)
    }

    override fun getByEmail(email: String): Customer? {
        return dao.getByEmail(email)
    }

    override fun getList(page: Int, size: Int): PageableData<Customer> {
        return dao.getList(page, size)
    }

    override fun update(customer: Customer): Int {
        return dao.update(customer)
    }

    override fun delete(customerId: Int): Boolean {
        var result = true

        transaction {
            val number = dao.delete(customerId)
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }

}