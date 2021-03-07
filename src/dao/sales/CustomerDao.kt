package com.giant_giraffe.dao.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.sales.customer.Customer
import com.giant_giraffe.data.sales.customer.CustomerEntity
import com.giant_giraffe.data.sales.customer.CustomerTable
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

object CustomerDao {

    fun create(customer: Customer): Customer {
        return transaction {
            CustomerEntity.new {
                firstName = customer.firstName!!
                lastName = customer.lastName!!
                email = customer.email!!
                password = customer.password!!
                phone = customer.phone
                street = customer.street
                city = customer.city
                state = customer.state
                zipCode = customer.zipCode
            }
        }.toModel()
    }

    fun getById(customerId: Int): Customer? {
        return transaction {
            CustomerEntity
                .find { CustomerTable.id eq customerId }
                .firstOrNull()
        }?.toModel()
    }

    fun getByEmail(email: String): Customer? {
        return transaction {
            CustomerEntity
                .find { CustomerTable.email eq email }
                .firstOrNull()
        }?.toModel()
    }

    fun find(page: Int, size: Int): PagedData<Customer> {
        var totalDataSize = 0

        val customers = transaction {
            val allData = CustomerEntity.all()
            totalDataSize = allData.count()

            allData
                .limit(size, offset = page * size)
                .map { it.toModel() }
        }

        val pageInfo = EntityUtility.getPageInfo(
            size = size,
            page = page,
            dataCount = customers.size,
            totalDataCount = totalDataSize,
        )

        return PagedData(
            data = customers,
            pageInfo = pageInfo
        )
    }

    fun update(customer: Customer): Int {
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

    fun delete(customerId: Int): Int {
        return transaction {
            CustomerTable.deleteWhere { CustomerTable.id eq customerId }
        }
    }

}