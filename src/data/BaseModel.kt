package com.giant_giraffe.data

/**
 * @property V View
 */
interface BaseModel<V> {

    fun toView(): V

}