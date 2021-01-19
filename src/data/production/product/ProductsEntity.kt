package com.giant_giraffe.data.production.product

import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class ProductsEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<Products, ProductsView> {

    companion object: IntEntityClass<ProductsEntity>(ProductsTable)

    var name by ProductsTable.name
    var modelYear by ProductsTable.modelYear
    var listPrice by ProductsTable.listPrice
    var brandId by ProductsTable.brandId
    var categoryId by ProductsTable.categoryId

    override fun toModel() =
        Products(this)

}