package com.giant_giraffe.data.production.category

import com.giant_giraffe.data.BaseModel

class Category(categoryEntity: CategoryEntity): BaseModel<CategoryView> {

    override fun toView(): CategoryView {
        return CategoryView()
    }

}