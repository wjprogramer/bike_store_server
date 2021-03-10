package com.giant_giraffe.data.sales.staff

import com.giant_giraffe.data.sales.store.StoreView

data class StaffView(
    var id:             Int?        = null,
    var firstName:      String?     = null,
    var lastName:       String?     = null,
    var email:          String?     = null,
    var phone:          String?     = null,
    var active:         Int?        = null,
    var storeId:        Int?        = null,
    var managerId:      Int?        = null,
    var store:          StoreView?  = null,
)