package com.giant_giraffe.services.sales.store

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.sales.store.Store

interface StoreService {

    fun create(store: Store): Store

    fun getById(storeId: Int): Store?

    fun getList(page: Int, size: Int): PagedData<Store>

    fun update(store: Store): Int

    fun delete(storeId: Int): Boolean

    fun getAll(): List<Store>

}