package com.giant_giraffe.data.sales.staff

import com.giant_giraffe.data.BaseModel

class Staff(staffEntity: StaffEntity): BaseModel<StaffView> {

    override fun toView(): StaffView {
        return StaffView()
    }

}