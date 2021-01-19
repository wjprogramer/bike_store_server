package com.giant_giraffe.data.production.product

import com.giant_giraffe.data.BaseModel

class Products(productsEntity: ProductsEntity): BaseModel<ProductsView> {

    var id = productsEntity.id.value
    var name = productsEntity.name
    var modelYear = productsEntity.modelYear
    var listPrice = productsEntity.listPrice
    var brandId = productsEntity.brandId.value
    var categoryId = productsEntity.categoryId.value

    override fun toView(): ProductsView {
        return ProductsView(
            id,
            name,
            modelYear.toString(),
            listPrice.toInt(),
            brandId,
            categoryId,
        )
    }

}