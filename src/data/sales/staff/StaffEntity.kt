package com.giant_giraffe.data.sales.staff

import com.giant_giraffe.data.BaseEntity
import com.giant_giraffe.data.sales.store.StoreEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import java.lang.Exception

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

    var store       by StoreEntity referencedOn StaffTable.storeId

    override fun toModel() =
        Staff(this)

    fun toDetailsModel(
        hasStore: Boolean = true
    ): Staff {
        val staff = Staff(this)

        if (hasStore) {
            staff.store = store.toModel()
        }

        return staff
    }

}