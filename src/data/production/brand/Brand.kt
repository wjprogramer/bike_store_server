package com.giant_giraffe.data.production.brand

import com.giant_giraffe.data.BaseModel
import com.giant_giraffe.data.production.category.CategoryEntity

class Brand(
    var id: Int? = null,
    var name: String? = null,
): BaseModel<BrandView> {

    constructor(brandEntity: BrandEntity): this(
        brandEntity.id.value,
        brandEntity.name
    )

    override fun toView(): BrandView {
        return BrandView(
            id,
            name,
        )
    }

}