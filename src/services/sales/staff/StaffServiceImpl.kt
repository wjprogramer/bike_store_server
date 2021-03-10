package com.giant_giraffe.services.sales.staff

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.sales.StaffDao
import com.giant_giraffe.data.sales.staff.Staff
import com.giant_giraffe.utility.PasswordUtility
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class StaffServiceImpl: StaffService {

    private val dao = StaffDao
    
    override fun create(staff: Staff): Staff {
        if (
            staff.firstName == null ||
            staff.lastName == null ||
            staff.email == null ||
            staff.password == null ||
            staff.active == null
        ) {
            throw Exception()
        }

        staff.password = PasswordUtility.encode(staff.password!!)

        return dao.create(staff)
    }

    override fun getById(staffId: Int): Staff? {
        return dao.getById(staffId)
    }

    override fun getByEmail(email: String): Staff? {
        return dao.getByEmail(email)
    }

    override fun find(page: Int,
                      size: Int,
                      storeId: Int?,): PagedData<Staff> {
        return dao.find(
            page,
            size,
            storeId = storeId,
        )
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