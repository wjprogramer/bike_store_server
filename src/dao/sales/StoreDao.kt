package com.giant_giraffe.dao.sales

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.sales.store.Store
import com.giant_giraffe.data.sales.store.StoreEntity
import com.giant_giraffe.data.sales.store.StoreTable
import com.giant_giraffe.data.sales.store.StoreView
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

object StoreDao {

    fun create(store: Store): Store {
        return transaction {
            StoreEntity.new {
                storeName = store.storeName!!
                phone = store.phone
                email = store.email
                street = store.street
                city = store.city
                state = store.state
                zipCode = store.zipCode
            }
        }.toModel()
    }

    fun getById(storeId: Int): Store? {
        return transaction {
            StoreEntity
                .find { StoreTable.id eq storeId }
                .firstOrNull()
        }?.toModel()
    }

    fun getList(page: Int, size: Int): PageableData<Store> {
        return transaction {
            val stores = StoreEntity.all()
                .limit(size, offset = page * size)
                .map { it.toModel() }

            val pageInfo = EntityUtility
                .getPageInfo(StoreEntity, page, size, stores.size)

            PageableData(
                data = stores,
                pageInfo = pageInfo
            )
        }
    }

    fun update(store: Store): Int {
        return transaction {
            StoreEntity
                .find { StoreTable.id eq store.id }
                .firstOrNull() ?: throw Exception()

            StoreTable.update({ StoreTable.id eq store.id }) {
                store.storeName?.let { e -> it[storeName] = e }
                store.phone?.let { e -> it[phone] = e }
                store.email?.let { e -> it[email] = e }
                store.street?.let { e -> it[street] = e }
                store.city?.let { e -> it[city] = e }
                store.state?.let { e -> it[state] = e }
                store.zipCode?.let { e -> it[zipCode] = e }
            }
        }
    }

    fun delete(storeId: Int): Int {
        return transaction {
            StoreTable.deleteWhere { StoreTable.id eq storeId }
        }
    }

}