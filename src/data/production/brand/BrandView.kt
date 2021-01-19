package com.giant_giraffe.data.production.brand

import kotlinx.serialization.Serializable

@Serializable
data class BrandView(
    var id: Int? = null,
    var name: String? = null,
)