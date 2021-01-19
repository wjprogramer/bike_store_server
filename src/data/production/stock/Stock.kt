package com.giant_giraffe.data.production.stock

import com.giant_giraffe.data.BaseModel

class Stock(stockEntity: StockEntity): BaseModel<StockView> {

    var id = stockEntity.id.value
    var quantity = stockEntity.quantity
    var storeId = stockEntity.storeId?.value
    var productId = stockEntity.productId?.value

    override fun toView(): StockView {
        return StockView(
            id,
            quantity,
            storeId,
            productId,
        )
    }

}