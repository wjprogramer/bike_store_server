package com.giant_giraffe.data.production.category

import com.giant_giraffe.data.BaseModel

class Category(categoryEntity: CategoryEntity): BaseModel<CategoryView> {

    var id = categoryEntity.id.value
    var name = categoryEntity.name

    override fun toView(): CategoryView {
        return CategoryView(
            id,
            name,
        )
    }

}