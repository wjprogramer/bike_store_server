package com.giant_giraffe.data.production.category

import com.giant_giraffe.data.BaseConverter
import io.ktor.http.*

object CategoryConverter: BaseConverter<CategoryEntity, Category, CategoryView> {

    override fun parametersToView(parameters: Parameters): CategoryView {
        val result = CategoryView()

        result.id =     parameters["id"]?.toIntOrNull()
        result.name =   parameters["name"]

        return result
    }

    override fun viewToModel(view: CategoryView) = Category(
        id =    view.id,
        name =  view.name,
    )

}