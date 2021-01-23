package com.giant_giraffe.data.production.product

import com.giant_giraffe.data.BaseConverter
import io.ktor.http.*

object ProductConverter: BaseConverter<ProductEntity, Product, ProductView> {

    override fun parametersToView(parameters: Parameters): ProductView {
        val result = ProductView()

        result.id = parameters["id"]?.toInt()
        result.name = parameters["name"]
        result.modelYear = parameters["model_year"]?.toIntOrNull()
        result.listPrice = parameters["list_price"]
        result.brandId = parameters["brand_id"]?.toInt()
        result.categoryId = parameters["category_id"]?.toInt()

        return result
    }

    override fun viewToModel(view: ProductView) = Product(
        view.id,
        view.name,
        view.modelYear,
        view.listPrice?.toBigDecimalOrNull(),
        view.brandId,
        view.categoryId,
    )

}