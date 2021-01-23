package com.giant_giraffe.data.sales.staff

import com.google.gson.annotations.SerializedName

data class StaffView(
    var id: Int? = null,
    @SerializedName("first_name")
    var firstName: String? = null,
    @SerializedName("last_name")
    var lastName: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var active: Int? = null,
    var storeId: Int? = null,
    var managerId: Int? = null,
)