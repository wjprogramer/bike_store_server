package com.giant_giraffe.data.production.brand

import com.giant_giraffe.data.BaseIntEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntityClass

class BrandEntity(id: EntityID<Int>): BaseIntEntity<Brand, BrandView>(BrandTable, id) {

    companion object: IntEntityClass<BrandEntity>(BrandTable)

    var name by BrandTable.name

    override fun toModel() =
        Brand(this)

}