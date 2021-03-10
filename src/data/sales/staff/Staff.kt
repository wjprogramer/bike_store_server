package com.giant_giraffe.data.sales.staff

import com.giant_giraffe.data.BaseModel
import com.giant_giraffe.data.sales.store.Store

class Staff(
    var id: Int? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null,
    var phone: String? = null,
    var active: Int? = null,
    var storeId: Int? = null,
    var managerId: Int? = null,
    var store: Store? = null,
): BaseModel<StaffView> {

    constructor(staffEntity: StaffEntity): this(
            id =                staffEntity.id.value,
            firstName =         staffEntity.firstName,
            lastName =          staffEntity.lastName,
            email =             staffEntity.email,
            password =          staffEntity.password,
            phone =             staffEntity.phone,
            active =            staffEntity.active,
            storeId =           staffEntity.storeId.value,
            managerId =         staffEntity.managerId?.value,
    )

    override fun toView(): StaffView {
        return StaffView(
            id =                id,
            firstName =         firstName,
            lastName =          lastName,
            email =             email,
            phone =             phone,
            active =            active,
            storeId =           storeId,
            managerId =         managerId,
            store =             store?.toView(),
        )
    }

}