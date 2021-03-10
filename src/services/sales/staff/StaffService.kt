package com.giant_giraffe.services.sales.staff

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.sales.staff.Staff

interface StaffService {

    fun create(staff: Staff): Staff

    fun getById(staffId: Int): Staff?

    fun getByEmail(email: String): Staff?

    fun find(page: Int,
             size: Int,
             storeId: Int? = null,
    ): PagedData<Staff>

    fun update(staff: Staff): Int

    fun delete(staffId: Int): Boolean
    
}