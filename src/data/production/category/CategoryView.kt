package com.giant_giraffe.data.production.category

import com.giant_giraffe.data.BaseView

data class CategoryView(
    var id:     Int?    = null,
    var name:   String? = null,
    var imageUrl: String? = null,
    var isDeleted: Boolean? = null,
): BaseView