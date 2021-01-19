package com.giant_giraffe.data.sales.store

import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class StoreEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<Store, StoreView> {

    companion object: IntEntityClass<StoreEntity>(StoreTable)

    override fun toModel() =
        Store(this)

}