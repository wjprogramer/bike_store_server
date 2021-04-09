package com.giant_giraffe.data.production.brand

import com.giant_giraffe.data.BaseModel

class Brand(
    var id: Int? = null,
    var name: String? = null,
    entity: BrandEntity? = null,
): BaseModel<Brand, BrandView>(entity) {

    constructor(entity: BrandEntity): this(
            id =        entity.id.value,
            name =      entity.name,
            entity =    entity,
    )

    override fun toView(): BrandView {
        val view = BrandView(
            id =    id,
            name =  name,
        )
        return super.appendBaseViewInfo(view)
    }

}