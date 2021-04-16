package com.giant_giraffe.data.production.brand

import com.giant_giraffe.data.BaseModel

class Brand(
    var id:         Int? = null,
    var name:       String? = null,
    var imageUrl:   String? = null,
    var isDeleted:  Boolean? = null,
): BaseModel<BrandView> {

    constructor(brandEntity: BrandEntity): this(
            id =        brandEntity.id.value,
            name =      brandEntity.name,
            imageUrl =  brandEntity.imageUrl,
            isDeleted =  brandEntity.isDeleted,
    )

    override fun toView(): BrandView {
        return BrandView(
            id =            id,
            name =          name,
            imageUrl =      imageUrl,
            isDeleted =     isDeleted,
        )
    }

}