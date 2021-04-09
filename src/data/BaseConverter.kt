package com.giant_giraffe.data

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*

/**
 * @property E Entity
 * @property M Model
 * @property V View
 */
interface BaseConverter<E, M: BaseModel<M, V>, V: BaseView> {

    fun parametersToView(parameters: Parameters): V

    fun appendBasePropertiesToView(view: V, parameters: Parameters): V {
        view.createdOn = parameters["created_on"]
        view.updatedOn = parameters["updated_on"]
        view.deletedOn = parameters["deleted_on"]
        view.createdBy = parameters["created_by"]
        view.updatedBy = parameters["updated_by"]
        view.deletedBy = parameters["deleted_by"]
        return view
    }

    fun viewToModel(view: V): M

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
    fun mapToView(mapping: Map<String, Any?>): V

    /**
     * Convert body of request to model
     */
    fun mapToModel(mapping: Map<String, Any?>): M {
        val view = mapToView(mapping)
        return viewToModel(view)
    }

}

suspend inline fun <E, M : BaseModel<M, V>, reified V : BaseView> BaseConverter<E, M, V>.receiveAndToModel(call: ApplicationCall): M {
    val view = call.receive<V>()
    return viewToModel(view)
}