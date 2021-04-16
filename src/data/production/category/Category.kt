package com.giant_giraffe.data.production.category

import com.giant_giraffe.data.BaseModel

class Category(
    var id:         Int? = null,
    var name:       String? = null,
    var imageUrl:   String? = null,
    var isDeleted:  Boolean? = null,
): BaseModel<CategoryView> {

    constructor(categoryEntity: CategoryEntity): this(
            id =    categoryEntity.id.value,
            name =  categoryEntity.name,
            imageUrl =  categoryEntity.imageUrl,
            isDeleted =  categoryEntity.isDeleted,
    )

    override fun toView(): CategoryView {
        return CategoryView(
            id =            id,
            name =          name,
            imageUrl =      imageUrl,
            isDeleted =     isDeleted,
        )
    }

}