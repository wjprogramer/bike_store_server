package com.giant_giraffe.data.production.category

import com.giant_giraffe.data.BaseModel

class Category(
    var id: Int? = null,
    var name: String? = null,
    entity: CategoryEntity? = null,
): BaseModel<Category, CategoryView>(entity) {

    constructor(entity: CategoryEntity): this(
            id =        entity.id.value,
            name =      entity.name,
            entity =    entity,
    )

    override fun toView(): CategoryView {
        val view = CategoryView(
            id =    id,
            name =  name,
        )
        return super.appendBaseViewInfo(view)
    }

}