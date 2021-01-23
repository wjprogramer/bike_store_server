package com.giant_giraffe.services.sales.store

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.sales.store.Store
import com.giant_giraffe.data.sales.store.StoreEntity
import com.giant_giraffe.data.sales.store.StoreTable
import com.giant_giraffe.data.sales.store.StoreView
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

class StoreServiceImpl: StoreService {

    override fun create(store: Store): StoreView {
        if (store.storeName.isNullOrEmpty()) {
            throw Exception("")
        }

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
        }.toView()
    }

    override fun getById(storeId: Int): StoreView? {
        return transaction {
            StoreEntity
                .find { StoreTable.id eq storeId }
                .firstOrNull()
        }?.toView()
    }

    override fun getList(page: Int, size: Int): PageableData<StoreView> {
        return transaction {
            val stores = StoreEntity.all()
                .limit(size, offset = page * size)
                .map { it.toView() }

            val pageInfo = EntityUtility
                .getPageInfo(StoreEntity, page, size, stores.size)

            PageableData(
                data = stores,
                pageInfo = pageInfo
            )
        }
    }

    override fun update(store: Store): Int {
        return transaction {
            StoreEntity
                .find { StoreTable.id eq store.id }
                .firstOrNull() ?: throw Exception()

            StoreTable.update({ StoreTable.id eq store.id }) {
                store.storeName?.let { e -> it[StoreTable.storeName] = e }
                store.phone?.let { e -> it[StoreTable.phone] = e }
                store.email?.let { e -> it[StoreTable.email] = e }
                store.street?.let { e -> it[StoreTable.street] = e }
                store.city?.let { e -> it[StoreTable.city] = e }
                store.state?.let { e -> it[StoreTable.state] = e }
                store.zipCode?.let { e -> it[StoreTable.zipCode] = e }
            }
        }
    }

    override fun delete(storeId: Int): Boolean {
        var result = true

        transaction {
            val number = StoreTable.deleteWhere { StoreTable.id eq storeId }
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }

}