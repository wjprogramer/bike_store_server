package com.giant_giraffe.services.sales.store

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.sales.StoreDao
import com.giant_giraffe.data.sales.store.Store
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class StoreServiceImpl: StoreService {

    private val dao = StoreDao

    override fun create(store: Store): Store {
        if (store.storeName.isNullOrEmpty()) {
            throw Exception("")
        }

        return dao.create(store)
    }

    override fun getById(storeId: Int): Store? {
        return dao.getById(storeId)
    }

    override fun getList(page: Int, size: Int): PagedData<Store> {
        return dao.getList(page, size)
    }

    override fun update(store: Store): Int {
        return dao.update(store)
    }

    override fun delete(storeId: Int): Boolean {
        var result = true

        transaction {
            val number = dao.delete(storeId)
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }

    override fun getAll(): List<Store> {
        return dao.getAll()
    }

}