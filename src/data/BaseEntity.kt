package com.giant_giraffe.data

/**
 * @property M Model
 * @property V View
 */
interface BaseEntity<M: BaseModel<V>, V> {

    /**
     * Convert entity to model that used in internal server
     */
    fun toModel(): M

    /**
     * Convert entity to view that provided to frontend
     */
    fun toView(): V = toModel().toView()

}