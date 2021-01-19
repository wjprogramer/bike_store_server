package com.giant_giraffe.data.production.brand

import com.giant_giraffe.data.BaseModel

class Brand(brandEntity: BrandEntity): BaseModel<BrandView> {

    override fun toView(): BrandView {
        return BrandView()
    }

}