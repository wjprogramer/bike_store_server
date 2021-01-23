package com.giant_giraffe.data.production.stock

import com.giant_giraffe.data.BaseModel

class Stock(
    var id: Int? = null,
    var quantity: Int? = null,
    var storeId: Int? = null,
    var productId: Int? = null,
): BaseModel<StockView> {

    constructor(stockEntity: StockEntity): this(
        stockEntity.id.value,
        stockEntity.quantity,
        stockEntity.storeId?.value,
        stockEntity.productId?.value,
    )

    override fun toView(): StockView {
        return StockView(
            id,
            quantity,
            storeId,
            productId,
        )
    }

}