package com.giant_giraffe.data

import io.ktor.http.*

/**
 * @property E Entity
 * @property M Model
 * @property V View
 */
interface BaseConverter<E, M, V> {

    fun parametersToView(parameters: Parameters): V

    fun viewToModel(view: V): M

    fun parametersToModel(parameters: Parameters): M {
        val view = parametersToView(parameters)
        return viewToModel(view)
    }

}