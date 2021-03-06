package com.giant_giraffe.data.sales.store

import com.google.gson.annotations.SerializedName

data class StoreView(
    var id: Int? = null,
    @SerializedName("store_name")
    var storeName: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var street: String? = null,
    var city: String? = null,
    var state: String? = null,
    @SerializedName("zip_code")
    var zipCode: String? = null,
)