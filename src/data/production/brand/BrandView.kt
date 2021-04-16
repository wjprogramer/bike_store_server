package com.giant_giraffe.data.production.brand

import com.giant_giraffe.data.BaseView

data class BrandView(
    var id:     Int?    = null,
    var name:   String? = null,
    var imageUrl: String? = null,
    var isDeleted: Boolean? = null,
): BaseView