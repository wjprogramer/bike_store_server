package com.giant_giraffe.services.sales.staff

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.sales.staff.Staff
import com.giant_giraffe.data.sales.staff.StaffView

interface StaffService {

    fun create(staff: Staff): StaffView

    fun getById(staffId: Int): StaffView?

    fun getList(page: Int, size: Int): PageableData<StaffView>

    fun update(staff: Staff): Int

    fun delete(staffId: Int): Boolean
    
}