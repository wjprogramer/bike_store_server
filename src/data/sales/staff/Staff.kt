package com.giant_giraffe.data.sales.staff

import com.giant_giraffe.data.BaseModel

class Staff(
    var id: Int? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var active: Int? = null,
    var storeId: Int? = null,
    var managerId: Int? = null,
): BaseModel<StaffView> {

    constructor(staffEntity: StaffEntity): this(
        staffEntity.id.value,
        staffEntity.firstName,
        staffEntity.lastName,
        staffEntity.email,
        staffEntity.phone,
        staffEntity.active,
        staffEntity.storeId.value,
        staffEntity.managerId?.value,
    )

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