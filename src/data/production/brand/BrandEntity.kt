package com.giant_giraffe.data.production.brand

import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class BrandEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<Brand, BrandView> {

    companion object: IntEntityClass<BrandEntity>(BrandTable)

    override fun toModel() =
        Brand(this)

}