package com.giant_giraffe.data.sales.store

data class StoreView(
    var id:             Int?        = null,
    var storeName:      String?     = null,
    var phone:          String?     = null,
    var email:          String?     = null,
    var street:         String?     = null,
    var city:           String?     = null,
    var state:          String?     = null,
    var zipCode:        String?     = null,
)