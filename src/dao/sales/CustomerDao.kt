package com.giant_giraffe.dao.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.BaseDao
import com.giant_giraffe.data.sales.customer.Customer
import com.giant_giraffe.data.sales.customer.CustomerEntity
import com.giant_giraffe.data.sales.customer.CustomerTable
import com.giant_giraffe.utils.ilike
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

object CustomerDao:
    BaseDao<Int, CustomerEntity, Customer>()
{

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
            }.toModel()
        }
    }

    fun getById(customerId: Int): Customer? {
        return transaction {
            CustomerEntity
                .find { CustomerTable.id eq customerId }
                .firstOrNull()
                ?.toModel()
        }
    }

    fun getByEmail(email: String): Customer? {
        return transaction {
            CustomerEntity
                .find { CustomerTable.email eq email }
                .firstOrNull()
                ?.toModel()
        }
    }

    fun find(page: Int,
             size: Int,
             keyword: String? = null,
    ): PagedData<Customer> {
        var predicates: Op<Boolean> = Op.build { Op.TRUE }

        return transaction {

            keyword?.let        {
                predicates = predicates and (
                        (CustomerTable.firstName ilike "%$it%") or
                                (CustomerTable.firstName ilike "%$it%") or
                                (CustomerTable.lastName ilike "%$it%") or
                                (CustomerTable.email ilike "%$it%") or
                                (CustomerTable.phone ilike "%$it%") or
                                (CustomerTable.street ilike "%$it%") or
                                (CustomerTable.city ilike "%$it%") or
                                (CustomerTable.state ilike "%$it%") or
                                (CustomerTable.zipCode ilike "%$it%")
                        )
            }

            CustomerEntity.findAndGetPagedData(
                page = page,
                size = size,
                predicates = predicates,
            )
        }
    }

    fun update(customer: Customer): Int {
        return transaction {
            CustomerEntity
                .find { CustomerTable.id eq customer.id }
                .firstOrNull() ?: throw Exception()

            CustomerTable.update({ CustomerTable.id eq customer.id }) {
                customer.firstName?.let     { e -> it[firstName] = e }
                customer.lastName?.let      { e -> it[lastName] = e }
                customer.email?.let         { e -> it[email] = e }
                customer.phone?.let         { e -> it[phone] = e }
                customer.street?.let        { e -> it[street] = e }
                customer.city?.let          { e -> it[city] = e }
                customer.state?.let         { e -> it[state] = e }
                customer.zipCode?.let       { e -> it[zipCode] = e }
            }
        }
    }

    fun delete(customerId: Int): Int {
        return transaction {
            CustomerTable.deleteWhere { CustomerTable.id eq customerId }
        }
    }

}