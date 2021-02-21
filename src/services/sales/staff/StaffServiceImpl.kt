package com.giant_giraffe.services.sales.staff

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.dao.sales.StaffDao
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

    private val dao = StaffDao
    
    override fun create(staff: Staff): Staff {
        if (
            staff.firstName == null ||
            staff.lastName == null ||
            staff.email == null ||
            staff.active == null
        ) {
            throw Exception()
        }

        return dao.create(staff)
    }

    override fun getById(staffId: Int): Staff? {
        return dao.getById(staffId)
    }

    override fun getByEmail(email: String): Staff? {
        return dao.getByEmail(email)
    }

    override fun getList(page: Int, size: Int): PageableData<Staff> {
        return dao.getList(page, size)
    }

    override fun update(staff: Staff): Int {
        return dao.update(staff)
    }

    override fun delete(staffId: Int): Boolean {
        var result = true

        transaction {
            val number = dao.delete(staffId)
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }
    
}