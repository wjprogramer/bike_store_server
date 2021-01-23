package com.giant_giraffe.data.production.product

import com.giant_giraffe.data.BaseModel
import java.math.BigDecimal

class Product(
    var id: Int? = null,
    var name: String? = null,
    var modelYear: Int? = null,
    var listPrice: BigDecimal? = null,
    var brandId: Int? = null,
    var categoryId: Int? = null,
): BaseModel<ProductView> {

    constructor(productEntity: ProductEntity): this(
        productEntity.id.value,
        productEntity.name,
        productEntity.modelYear,
        productEntity.listPrice,
        productEntity.brandId.value,
        productEntity.categoryId.value,
    )

    override fun toView(): ProductView {
        return ProductView(
            id,
            name,
            modelYear,
            listPrice?.toString(),
            brandId,
            categoryId,
        )
    }

}