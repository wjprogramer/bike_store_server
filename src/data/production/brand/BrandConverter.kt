package com.giant_giraffe.data.production.brand

import com.giant_giraffe.data.BaseConverter
import io.ktor.application.*
import io.ktor.http.*

object BrandConverter: BaseConverter<BrandEntity, Brand, BrandView> {

    override fun parametersToView(parameters: Parameters): BrandView {
        val result = BrandView()

        result.id =             parameters["id"]?.toIntOrNull()
        result.name =           parameters["name"]
        result.imageUrl =       parameters["image_url"]
        result.isDeleted =      parameters["is_deleted"]?.toBoolean()

        return result
    }

    override fun viewToModel(view: BrandView) = Brand(
        id =            view.id,
        name =          view.name,
        imageUrl =      view.imageUrl,
        isDeleted =     view.isDeleted,
    )

    override fun mapToView(mapping: Map<String, Any?>): BrandView {
        TODO("Not yet implemented")
    }

}