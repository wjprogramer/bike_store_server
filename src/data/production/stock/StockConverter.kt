package com.giant_giraffe.data.production.stock

import com.giant_giraffe.data.BaseConverter
import io.ktor.http.*

object StockConverter: BaseConverter<StockEntity, Stock, StockView> {

    override fun parametersToView(parameters: Parameters): StockView {
        val result = StockView()

        result.id =         parameters["id"]?.toIntOrNull()
        result.quantity =   parameters["quantity"]?.toIntOrNull()
        result.storeId =    parameters["store_id"]?.toIntOrNull()
        result.productId =  parameters["product_id"]?.toIntOrNull()

        return result
    }

    override fun viewToModel(view: StockView) = Stock(
        id =            view.id,
        quantity =      view.quantity,
        storeId =       view.storeId,
        productId =     view.productId,
    )

}