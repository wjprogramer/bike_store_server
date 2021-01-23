package com.giant_giraffe.data.production.category

import com.giant_giraffe.data.BaseModel

class Category(
    var id: Int? = null,
    var name: String? = null,
): BaseModel<CategoryView> {

    constructor(categoryEntity: CategoryEntity): this(
        categoryEntity.id.value,
        categoryEntity.name
    )

    override fun toView(): CategoryView {
        return CategoryView(
            id,
            name,
        )
    }

}