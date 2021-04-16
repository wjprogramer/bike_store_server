package com.giant_giraffe.data.production.category

import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class CategoryEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<Category, CategoryView> {

    companion object: IntEntityClass<CategoryEntity>(CategoryTable)

    var name by CategoryTable.name
    var imageUrl by CategoryTable.imageUrl
    var isDeleted by CategoryTable.isDeleted

    override fun toModel() =
        Category(this)

}