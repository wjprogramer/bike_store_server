package com.giant_giraffe.data.production.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryView(
    var id: Int? = null,
    var name: String? = null,
)