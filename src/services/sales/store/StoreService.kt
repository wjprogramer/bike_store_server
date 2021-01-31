package com.giant_giraffe.services.sales.store

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.sales.store.Store
import com.giant_giraffe.data.sales.store.StoreView

interface StoreService {

    fun create(store: Store): Store

    fun getById(storeId: Int): Store?

    fun getList(page: Int, size: Int): PageableData<Store>

    fun update(store: Store): Int

    fun delete(storeId: Int): Boolean

}