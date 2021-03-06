package com.giant_giraffe.dao.sales

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.sales.staff.Staff
import com.giant_giraffe.data.sales.staff.StaffEntity
import com.giant_giraffe.data.sales.staff.StaffTable
import com.giant_giraffe.data.sales.store.StoreTable
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

object StaffDao {

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
            }
        }.toModel()
    }

    fun getById(staffId: Int): Staff? {
        return transaction {
            StaffEntity
                .find { StaffTable.id eq staffId }
                .firstOrNull()
        }?.toModel()
    }

    fun getByEmail(email: String): Staff? {
        return transaction {
            StaffEntity
                .find { StaffTable.email eq email }
                .firstOrNull()
        }?.toModel()
    }

    fun getList(page: Int, size: Int): PagedData<Staff> {
        return transaction {
            val staffs = StaffEntity.all()
                .limit(size, offset = page * size)
                .map { it.toModel() }

            val pageInfo = EntityUtility
                .getPageInfo(StaffEntity, page, size, staffs.size)

            PagedData(
                data = staffs,
                pageInfo = pageInfo
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