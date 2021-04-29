package com.giant_giraffe.data

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.SortOrder

/**
 * @property E Entity
 * @property M Model
 * @property V View
 */
abstract class BaseConverter<E, M: BaseModel<V>, V: BaseView> {

    abstract fun parametersToView(parameters: Parameters): V

    abstract fun viewToModel(view: V): M

    /**
     * Parse form data
     *
     * Example:
     *
     * ```kt
     * val form = call.receiveParameters()
     * val brand = BrandConverter.parametersToModel(form)
     * ```
     */
    @Deprecated("Use `receiveAndToModel`")
    fun parametersToModel(parameters: Parameters): M {
        val view = parametersToView(parameters)
        return viewToModel(view)
    }

    /**
     * Convert body of request to view
     */
    abstract fun mapToView(mapping: Map<String, Any?>): V

    /**
     * Convert body of request to model
     */
    open fun mapToModel(mapping: Map<String, Any?>): M {
        val view = mapToView(mapping)
        return viewToModel(view)
    }

    open fun parseSortString(queryString: String?): Collection<Pair<Expression<*>, SortOrder>> {
        TODO("Not yet implemented")
    }

    protected fun parseSortQueryString(queryString: String?): Collection<Pair<String, SortOrder>> {
        val sortList = mutableListOf<Pair<String, SortOrder>>()
        if (queryString.isNullOrBlank()) {
            return sortList
        }

        val options = queryString.split(",")
        for (option in options) {
            val keyAndOrder = option.split(":")
            if (keyAndOrder.size != 2) {
                continue
            }
            val fieldName = keyAndOrder.getOrNull(0)
            val sortOrder = if (keyAndOrder.getOrNull(1) == SortOrder.ASC.name.toLowerCase()) {
                SortOrder.ASC
            } else {
                SortOrder.DESC
            }

            if (fieldName.isNullOrBlank()) {
                continue
            }
            sortList.add(fieldName to sortOrder)
        }

        return sortList
    }

}

/**
 * Usage:
 *
 * ```kotlin
 * post("/update") {
 *      val product = ProductConverter.receiveAndToModel(call)
 * }
 * ```
 */
suspend inline fun <E, M : BaseModel<V>, reified V : BaseView> BaseConverter<E, M, V>.receiveAndToModel(call: ApplicationCall): M {
    val view = call.receive<V>()
    return viewToModel(view)
}