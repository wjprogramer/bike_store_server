package com.giant_giraffe.data.sales.store

import com.giant_giraffe.data.BaseModel

class Store(storeEntity: StoreEntity): BaseModel<StoreView> {

    var id = storeEntity.id.value
    var storeName = storeEntity.storeName
    var phone = storeEntity.phone
    var email = storeEntity.email
    var street = storeEntity.street
    var city = storeEntity.city
    var state = storeEntity.state
    var zipCode = storeEntity.zipCode

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