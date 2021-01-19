package com.giant_giraffe.data.production.product

import kotlinx.serialization.Serializable

@Serializable
data class ProductView(
    var id: Int? = null,
    var name: String? = null,
    var modelYear: String? = null,
    var listPrice: Int? = null,
    var brandId: Int? = null,
    var categoryId: Int? = null,
)