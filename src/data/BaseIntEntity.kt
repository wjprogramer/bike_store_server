package com.giant_giraffe.data

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @property M Model
 * @property V View
 */
open class BaseIntEntity<M: BaseModel<M, V>, V>(table: BaseIntIdTable, id: EntityID<Int>) : IntEntity(id) {

    val createdOn by table.createdOn
    val updatedOn by table.updatedOn
    val deletedOn by table.deletedOn

    val createdBy by table.createdBy
    val updatedBy by table.updatedBy
    val deletedBy by table.deletedBy

    /**
     * Convert entity to model that used in internal server
     */
    open fun toModel(): M {
        TODO("Not yet implemented")
    }

    /**
     * Convert entity to view that provided to frontend
     */
    fun toView(): V = toModel().toView()

}