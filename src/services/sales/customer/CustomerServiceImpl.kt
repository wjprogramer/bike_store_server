package com.giant_giraffe.services.sales.customer

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.sales.CustomerDao
import com.giant_giraffe.data.sales.customer.Customer
import com.giant_giraffe.utility.PasswordUtility
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class CustomerServiceImpl: CustomerService {

    private val dao = CustomerDao

    override fun create(customer: Customer): Customer {
        if (
            customer.firstName == null ||
            customer.lastName == null ||
            customer.email == null ||
            customer.password == null
        ) {
            throw Exception("")
        }

        customer.password = PasswordUtility.encode(customer.password!!)

        return dao.create(customer)
    }

    override fun getById(customerId: Int): Customer? {
        return dao.getById(customerId)
    }

    override fun getByEmail(email: String): Customer? {
        return dao.getByEmail(email)
    }

    override fun find(page: Int, size: Int): PagedData<Customer> {
        return dao.find(page, size)
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