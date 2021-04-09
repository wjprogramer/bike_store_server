package com.giant_giraffe.data

import com.giant_giraffe.data.sales.staff.StaffTable
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

open class BaseIntIdTable(tableName: String): IntIdTable(tableName) {
    val createdOn = datetime("created_on")
    val updatedOn = datetime("updated_on")
    val deletedOn = datetime("deleted_on")

    val createdBy = reference(
        "created_by", StaffTable,
        onDelete = ReferenceOption.NO_ACTION,
        onUpdate = ReferenceOption.NO_ACTION
    ).nullable()

    val updatedBy = reference(
        "created_by", StaffTable,
        onDelete = ReferenceOption.NO_ACTION,
        onUpdate = ReferenceOption.NO_ACTION
    ).nullable()

    val deletedBy = reference(
        "created_by", StaffTable,
        onDelete = ReferenceOption.NO_ACTION,
        onUpdate = ReferenceOption.NO_ACTION
    ).nullable()
}