package com.giant_giraffe.services.sales.staff

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.sales.staff.Staff
import com.giant_giraffe.data.sales.staff.StaffEntity
import com.giant_giraffe.data.sales.staff.StaffTable
import com.giant_giraffe.data.sales.staff.StaffView
import com.giant_giraffe.data.sales.store.StoreTable
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

class StaffServiceImpl: StaffService {
    
    override fun create(staff: Staff): StaffView {
        if (
            staff.firstName == null ||
            staff.lastName == null ||
            staff.email == null ||
            staff.active == null
        ) {
            throw Exception()
        }

        return transaction {
            StaffEntity.new {
                firstName = staff.firstName!!
                lastName = staff.lastName!!
                email = staff.email!!
                phone = staff.phone
                active = staff.active!!
                storeId = EntityID(staff.storeId, StoreTable)
                staff.managerId?.let { managerId = EntityID(staff.managerId, StaffTable) }
            }
        }.toView()
    }

    override fun getById(staffId: Int): StaffView? {
        return transaction {
            StaffEntity
                .find { StaffTable.id eq staffId }
                .firstOrNull()
        }?.toView()
    }

    override fun getList(page: Int, size: Int): PageableData<StaffView> {
        return transaction {
            val staffs = StaffEntity.all()
                .limit(size, offset = page * size)
                .map { it.toView() }

            val pageInfo = EntityUtility
                .getPageInfo(StaffEntity, page, size, staffs.size)

            PageableData(
                data = staffs,
                pageInfo = pageInfo
            )
        }
    }

    override fun update(staff: Staff): Int {
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

    override fun delete(staffId: Int): Boolean {
        var result = true

        transaction {
            val number = StaffTable.deleteWhere { StaffTable.id eq staffId }
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }
    
}