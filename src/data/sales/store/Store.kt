package com.giant_giraffe.data.sales.store

import com.giant_giraffe.data.BaseModel

class Store(
    var id: Int? = null,
    var storeName: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var street: String? = null,
    var city: String? = null,
    var state: String? = null,
    var zipCode: String? = null,
): BaseModel<StoreView> {

    constructor(storeEntity: StoreEntity): this(
        id = storeEntity.id.value,
        storeName = storeEntity.storeName,
        phone = storeEntity.phone,
        email = storeEntity.email,
        street = storeEntity.street,
        city = storeEntity.city,
        state = storeEntity.state,
        zipCode = storeEntity.zipCode,
    )

    override fun toView(): StoreView {
        return StoreView(
            id,
            storeName,
            phone,
            email,
            street,
            city,
            state,
            zipCode,
        )
    }

}