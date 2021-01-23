package com.giant_giraffe.data.production.product

import com.google.gson.annotations.SerializedName

data class ProductView(
    var id: Int? = null,
    var name: String? = null,
    @SerializedName("model_year")
    var modelYear: Int? = null,
    @SerializedName("list_price")
    var listPrice: String? = null,
    @SerializedName("brand_id")
    var brandId: Int? = null,
    @SerializedName("category_id")
    var categoryId: Int? = null,
)