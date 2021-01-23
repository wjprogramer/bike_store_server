package com.giant_giraffe.services.sales.customer

import com.giant_giraffe.core.PageableData
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

    override fun create(customer: Customer): CustomerView {
        if (
            customer.firstName == null ||
            customer.lastName == null ||
            customer.email == null
        ) {
            throw Exception("")
        }

        return transaction {
            CustomerEntity.new {
                firstName = customer.firstName!!
                lastName = customer.lastName!!
                email = customer.email!!
                phone = customer.phone
                street = customer.street
                city = customer.city
                state = customer.state
                zipCode = customer.zipCode
            }
        }.toView()
    }

    override fun getById(customerId: Int): CustomerView? {
        return transaction {
            CustomerEntity
                .find { CustomerTable.id eq customerId }
                .firstOrNull()
        }?.toView()
    }

    override fun getList(page: Int, size: Int): PageableData<CustomerView> {
        return transaction {
            val staffs = CustomerEntity.all()
                .limit(size, offset = page * size)
                .map { it.toView() }

            val pageInfo = EntityUtility
                .getPageInfo(CustomerEntity, page, size, staffs.size)

            PageableData(
                data = staffs,
                pageInfo = pageInfo
            )
        }
    }

    override fun update(customer: Customer): Int {
        return transaction {
            CustomerEntity
                .find { CustomerTable.id eq customer.id }
                .firstOrNull() ?: throw Exception()

            CustomerTable.update({ CustomerTable.id eq customer.id }) {
                customer.firstName?.let { e -> it[firstName] = e }
                customer.lastName?.let { e -> it[lastName] = e }
                customer.email?.let { e -> it[email] = e }
                customer.phone?.let { e -> it[phone] = e }
                customer.street?.let { e -> it[street] = e }
                customer.city?.let { e -> it[city] = e }
                customer.state?.let { e -> it[state] = e }
                customer.zipCode?.let { e -> it[zipCode] = e }
            }
        }
    }

    override fun delete(customerId: Int): Boolean {
        var result = true

        transaction {
            val number = CustomerTable.deleteWhere { CustomerTable.id eq customerId }
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }

}