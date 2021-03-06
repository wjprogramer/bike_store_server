package com.giant_giraffe.data.sales.customer

import com.giant_giraffe.data.BaseView

data class CustomerView(
    var id:             Int?        = null,
    var firstName:      String?     = null,
    var lastName:       String?     = null,
    var email:          String?     = null,
    var phone:          String?     = null,
    var street:         String?     = null,
    var city:           String?     = null,
    var state:          String?     = null,
    var zipCode:        String?     = null,
    var avatarName:     String?     = null,
): BaseView