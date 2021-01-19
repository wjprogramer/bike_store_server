package com.giant_giraffe.data.sales.staff

import com.giant_giraffe.data.BaseModel

class Staff(staffEntity: StaffEntity): BaseModel<StaffView> {

    var id = staffEntity.id.value
    var firstName = staffEntity.firstName
    var lastName = staffEntity.lastName
    var email = staffEntity.email
    var phone = staffEntity.phone
    var active = staffEntity.active
    var storeId = staffEntity.storeId.value
    var managerId = staffEntity.managerId?.value

    override fun toView(): StaffView {
        return StaffView(
            id,
            firstName,
            lastName,
            email,
            phone,
            active,
            storeId,
            managerId,
        )
    }

}