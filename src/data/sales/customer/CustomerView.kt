package com.giant_giraffe.data.sales.customer

import com.google.gson.annotations.SerializedName

data class CustomerView(
    var id: Int? = null,
    @SerializedName("first_name")
    var firstName: String? = null,
    @SerializedName("last_name")
    var lastName: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var street: String? = null,
    var city: String? = null,
    var state: String? = null,
    @SerializedName("zip_code")
    var zipCode: String? = null,
)