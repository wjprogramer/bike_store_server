package com.giant_giraffe.data.production.product

import com.giant_giraffe.data.BaseConverter
import io.ktor.http.*
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.SortOrder

object ProductConverter: BaseConverter<ProductEntity, Product, ProductView>() {

    override fun parametersToView(parameters: Parameters): ProductView {
        val result = ProductView()

        result.id           = parameters["id"]?.toInt()
        result.name         = parameters["name"]
        result.modelYear    = parameters["model_year"]?.toIntOrNull()
        result.listPrice    = parameters["list_price"]
        result.brandId      = parameters["brand_id"]?.toInt()
        result.categoryId   = parameters["category_id"]?.toInt()
        // TODO: imagesUrls, visible, enable, isDeleted

        return result
    }

    override fun viewToModel(view: ProductView) = Product(
        id =            view.id,
        name =          view.name,
        modelYear =     view.modelYear,
        listPrice =     view.listPrice?.toBigDecimalOrNull(),
        brandId =       view.brandId,
        categoryId =    view.categoryId,
        imagesUrls =    view.imagesUrls?.toMutableList(),
        visible =       view.visible,
        enable =        view.enable,
        isDeleted =     view.isDeleted,
    )

    override fun mapToView(mapping: Map<String, Any?>): ProductView {
        TODO("Not yet implemented")
    }

    override fun parseSortString(queryString: String?): Collection<Pair<Expression<*>, SortOrder>> {
        return parseSortQueryString(queryString)
            .map {
                val field = when(it.first) {
                    "id" -> ProductTable.id
                    "name" -> ProductTable.name
                    "model_year" -> ProductTable.modelYear
                    "list_price" -> ProductTable.listPrice
                    "brand_id" -> ProductTable.brandId
                    "category_id" -> ProductTable.categoryId
                    "images_urls" -> ProductTable.imagesUrls
                    "visible" -> ProductTable.visible
                    "enable" -> ProductTable.enable
                    "is_deleted" -> ProductTable.isDeleted
                    else -> null
                } ?: return@map null
                val sortOrder = it.second

                field to sortOrder
            }
            .filterNotNull()
            .toList()
    }
}