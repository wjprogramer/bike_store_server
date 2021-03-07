package com.giant_giraffe.data.production.stock

import com.giant_giraffe.data.BaseModel
import com.giant_giraffe.data.production.product.Product
import com.giant_giraffe.data.sales.store.Store

class Stock(
    var id: Int? = null,
    var quantity: Int? = null,
    var storeId: Int? = null,
    var productId: Int? = null,
    var product: Product? = null,
    var store: Store? = null,
): BaseModel<StockView> {

    constructor(
        stockEntity: StockEntity,
        product: Product? = null,
        store: Store? = null,
    ): this(
            id =            stockEntity.id.value,
            quantity =      stockEntity.quantity,
            storeId =       stockEntity.storeId?.value,
            productId =     stockEntity.productId.value,
            product =       product,
            store =         store,
    )

    override fun toView(): StockView {
        val productView = product?.toView()
        val storeView = store?.toView()

        return StockView(
            id =            id,
            quantity =      quantity,
            storeId =       storeId,
            productId =     productId,
            product =       productView,
            store =         storeView,
        )
    }

}