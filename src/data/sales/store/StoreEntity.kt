package com.giant_giraffe.data.sales.store

import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class StoreEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<Store, StoreView> {

    companion object: IntEntityClass<StoreEntity>(StoreTable)

    var storeName by StoreTable.storeName
    var phone by StoreTable.phone
    var email by StoreTable.email
    var street by StoreTable.street
    var city by StoreTable.city
    var state by StoreTable.state
    var zipCode by StoreTable.zipCode

    override fun toModel() =
        Store(this)

}