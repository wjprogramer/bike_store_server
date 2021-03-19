package com.giant_giraffe.data.production.product

import com.giant_giraffe.data.BaseView

data class ProductView(
    var id:         Int?        = null,
    var name:       String?     = null,
    var modelYear:  Int?        = null,
    var listPrice:  String?     = null,
    var brandId:    Int?        = null,
    var categoryId: Int?        = null,
): BaseView