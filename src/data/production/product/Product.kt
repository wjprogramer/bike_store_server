package com.giant_giraffe.data.production.product

import com.giant_giraffe.data.BaseModel

class Product(productEntity: ProductEntity): BaseModel<ProductView> {

    var id = productEntity.id.value
    var name = productEntity.name
    var modelYear = productEntity.modelYear
    var listPrice = productEntity.listPrice
    var brandId = productEntity.brandId.value
    var categoryId = productEntity.categoryId.value

    override fun toView(): ProductView {
        return ProductView(
            id,
            name,
            modelYear.toString(),
            listPrice.toInt(),
            brandId,
            categoryId,
        )
    }

}