package com.giant_giraffe.data.production.brand

import com.giant_giraffe.data.BaseConverter
import io.ktor.http.*

object BrandConverter: BaseConverter<BrandEntity, Brand, BrandView> {

    override fun parametersToView(parameters: Parameters): BrandView {
        val result = BrandView()

        result.id = parameters["id"]?.toIntOrNull()
        result.name = parameters["name"]

        return result
    }

    override fun viewToModel(view: BrandView) = Brand(
        view.id,
        view.name,
    )

}