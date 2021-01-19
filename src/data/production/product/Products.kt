package com.giant_giraffe.data.production.product

import com.giant_giraffe.data.BaseModel

class Products(productsEntity: ProductsEntity): BaseModel<ProductsView> {

    override fun toView(): ProductsView {
        return ProductsView()
    }

}