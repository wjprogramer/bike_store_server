package com.giant_giraffe.data

import com.giant_giraffe.utility.DateTimeUtility
import org.joda.time.DateTime

/**
 * @property V View
 */
open class BaseModel<M: BaseModel<M, V>, V>(
    val entity: BaseIntEntity<M, V>? = null,
    var createdOn: DateTime? = null,
    var updatedOn: DateTime? = null,
    var deletedOn: DateTime? = null,
    var createdBy: DateTime? = null,
    var updatedBy: DateTime? = null,
    var deletedBy: DateTime? = null,
) {
    open fun toView(): V {
        TODO("Not yet implemented")
    }

    fun <T: BaseView> appendBaseViewInfo(view: T): T {
        view.createdOn = DateTimeUtility.convertToString(createdOn)
        view.updatedOn = DateTimeUtility.convertToString(updatedOn)
        view.deletedOn = DateTimeUtility.convertToString(deletedOn)
        view.createdBy = DateTimeUtility.convertToString(createdBy)
        view.updatedBy = DateTimeUtility.convertToString(updatedBy)
        view.deletedBy = DateTimeUtility.convertToString(deletedBy)
        return view
    }
}