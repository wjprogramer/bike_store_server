package com.giant_giraffe.data.production.stock

import com.giant_giraffe.data.BaseModel

class Stock(stockEntity: StockEntity): BaseModel<StockView> {

    override fun toView(): StockView {
        return StockView()
    }

}