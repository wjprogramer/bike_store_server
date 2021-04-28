package com.giant_giraffe.dao.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.BaseDao
import com.giant_giraffe.data.sales.staff.Staff
import com.giant_giraffe.data.sales.staff.StaffEntity
import com.giant_giraffe.data.sales.staff.StaffTable
import com.giant_giraffe.data.sales.store.StoreTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

object StaffDao:
    BaseDao<Int, StaffEntity, Staff>()
{

    fun create(staff: Staff): Staff {
        return transaction {
            StaffEntity.new {
                firstName = staff.firstName!!
                lastName = staff.lastName!!
                email = staff.email!!
                password = staff.password!!
                phone = staff.phone
                active = staff.active!!
                storeId = EntityID(staff.storeId, StoreTable)
                staff.managerId?.let { managerId = EntityID(staff.managerId, StaffTable) }
            }.toModel()
        }
    }

    fun getById(staffId: Int): Staff? {
        return transaction {
            StaffEntity
                .find { StaffTable.id eq staffId }
                .firstOrNull()
                ?.toModel()
        }
    }

    fun getByEmail(email: String): Staff? {
        return transaction {
            StaffEntity
                .find { StaffTable.email eq email }
                .firstOrNull()
                ?.toModel()
        }
    }

    fun find(page: Int,
             size: Int,
             storeId: Int? = null
    ): PagedData<Staff> {
        var predicates: Op<Boolean> = Op.build { Op.TRUE }
        val needLoadStore = storeId == null

        storeId?.let { predicates = predicates and (StaffTable.storeId eq it) }

        return StaffEntity.findAndGetPagedData(
            page = page,
            size = size,
            predicates = predicates,
            order = arrayOf(StaffTable.id to SortOrder.ASC)
        ) {
            if (needLoadStore) {
                it.load(StaffEntity::store)
            }
            it.toDetailsModel(
                hasStore = needLoadStore
            )
        }
    }

    fun update(staff: Staff): Int {
        return transaction {
            StaffEntity
                .find { StaffTable.id eq staff.id }
                .firstOrNull() ?: throw Exception()

            StaffTable.update({ StaffTable.id eq staff.id }) {
                staff.firstName?.let { e -> it[firstName] = e }
                staff.lastName?.let { e -> it[lastName] = e }
                staff.email?.let { e -> it[email] = e }
                staff.phone?.let { e -> it[phone] = e }
                staff.active?.let { e -> it[active] = e }
                staff.storeId?.let { e -> it[storeId] = EntityID(e, StoreTable) }
                staff.managerId?.let { e -> it[managerId] = EntityID(e, StaffTable) }
            }
        }
    }

    fun delete(staffId: Int): Int {
        return transaction {
            StaffTable.deleteWhere { StaffTable.id eq staffId }
        }
    }

}