package com.giant_giraffe.services.sales.staff

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.sales.staff.Staff
import com.giant_giraffe.data.sales.staff.StaffView

interface StaffService {

    fun create(staff: Staff): Staff

    fun getById(staffId: Int): Staff?

    fun getList(page: Int, size: Int): PageableData<Staff>

    fun update(staff: Staff): Int

    fun delete(staffId: Int): Boolean
    
}