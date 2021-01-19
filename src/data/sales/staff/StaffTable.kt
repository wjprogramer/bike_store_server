package com.giant_giraffe.data.sales.staff

import com.giant_giraffe.data.sales.store.StoreTable
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object StaffTable: IntIdTable("staffs") {
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val email = varchar("email", 255).uniqueIndex()
    val phone = varchar("phone", 25).nullable()
    val active = integer("active")

    val storeId = reference(
        "store_id", StoreTable.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )

    val managerId = reference(
        "manager_id", StaffTable.id,
        onDelete = ReferenceOption.NO_ACTION,
        onUpdate = ReferenceOption.NO_ACTION
    ).nullable()
}