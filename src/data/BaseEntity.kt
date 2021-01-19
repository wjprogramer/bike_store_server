package com.giant_giraffe.data

/**
 * @property M Model
 * @property V View
 */
interface BaseEntity<M: BaseModel<V>, V> {

    fun toModel(): M

    fun toView(): V = toModel().toView()

}