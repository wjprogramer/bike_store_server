package com.giant_giraffe.data.sales.store

import com.giant_giraffe.data.BaseModel

class Store(storeEntity: StoreEntity): BaseModel<StoreView> {

    override fun toView(): StoreView {
        return StoreView()
    }

}