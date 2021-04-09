package com.giant_giraffe.data.production.category

import com.giant_giraffe.data.BaseEntity
import com.giant_giraffe.data.BaseIntEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class CategoryEntity(id: EntityID<Int>): BaseIntEntity<Category, CategoryView>(CategoryTable, id) {

    companion object: IntEntityClass<CategoryEntity>(CategoryTable)

    var name by CategoryTable.name

    override fun toModel() =
        Category(this)

}