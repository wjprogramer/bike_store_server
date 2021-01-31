package com.giant_giraffe.services.sales.store

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.dao.sales.StoreDao
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

    override fun getList(page: Int, size: Int): PageableData<Store> {
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

}