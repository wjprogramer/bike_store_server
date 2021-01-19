package com.giant_giraffe.data.sales.staff

import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class StaffEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<Staff, StaffView> {

    companion object: IntEntityClass<StaffEntity>(StaffTable)

    override fun toModel() =
        Staff(this)

}