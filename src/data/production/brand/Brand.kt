package com.giant_giraffe.data.production.brand

import com.giant_giraffe.data.BaseModel

class Brand(brandEntity: BrandEntity): BaseModel<BrandView> {

    var id = brandEntity.id.value
    var name = brandEntity.name

    override fun toView(): BrandView {
        return BrandView(
            id,
            name,
        )
    }

}