package com.giant_giraffe.data.production.product

import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class ProductEntity(id: EntityID<Int>): IntEntity(id), BaseEntity<Product, ProductView> {

    companion object: IntEntityClass<ProductEntity>(ProductTable)

    var name by ProductTable.name
    var modelYear by ProductTable.modelYear
    var listPrice by ProductTable.listPrice
    var brandId by ProductTable.brandId
    var categoryId by ProductTable.categoryId

    override fun toModel() =
        Product(this)

}