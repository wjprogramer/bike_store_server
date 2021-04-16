package com.giant_giraffe.data.production.category

import com.giant_giraffe.data.BaseConverter
import io.ktor.http.*

object CategoryConverter: BaseConverter<CategoryEntity, Category, CategoryView> {

    override fun parametersToView(parameters: Parameters): CategoryView {
        val result = CategoryView()

        result.id =     parameters["id"]?.toIntOrNull()
        result.name =   parameters["name"]
        result.imageUrl =       parameters["image_url"]
        result.isDeleted =      parameters["is_deleted"]?.toBoolean()

        return result
    }

    override fun viewToModel(view: CategoryView) = Category(
        id =            view.id,
        name =          view.name,
        imageUrl =      view.imageUrl,
        isDeleted =     view.isDeleted,
    )

    override fun mapToView(mapping: Map<String, Any?>): CategoryView {
        TODO("Not yet implemented")
    }

}