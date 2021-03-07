package com.giant_giraffe.data.sales.staff

import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class StaffEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<Staff, StaffView> {

    companion object: IntEntityClass<StaffEntity>(StaffTable)

    var firstName   by StaffTable.firstName
    var lastName    by StaffTable.lastName
    var email       by StaffTable.email
    var password    by StaffTable.password
    var phone       by StaffTable.phone
    var active      by StaffTable.active
    var storeId     by StaffTable.storeId
    var managerId   by StaffTable.managerId

    override fun toModel() =
        Staff(this)

}