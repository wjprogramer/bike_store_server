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
    var imagesUrls: MutableCollection<String>? = null,
): BaseModel<ProductView> {

    constructor(productEntity: ProductEntity): this(
            id =            productEntity.id.value,
            name =          productEntity.name,
            modelYear =     productEntity.modelYear,
            listPrice =     productEntity.listPrice,
            brandId =       productEntity.brandId.value,
            categoryId =    productEntity.categoryId.value,
            imagesUrls =    productEntity.imagesUrls.toMutableList(),
    )

    override fun toView(): ProductView {
        return ProductView(
            id =            id,
            name =          name,
            modelYear =     modelYear,
            listPrice =     listPrice?.toString(),
            brandId =       brandId,
            categoryId =    categoryId,
            imagesUrls =    imagesUrls?.toTypedArray()
        )
    }

}