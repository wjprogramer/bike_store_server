package com.giant_giraffe.utils

import com.giant_giraffe.data.production.product.ProductTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

//fun Op<Boolean>?.or(x: Op<Boolean>) = this?.or(x) ?: x

fun <V> Op<Boolean>.tryAnd(
    value: V?,
    op: SqlExpressionBuilder.() -> Op<Boolean>
): Op<Boolean> {
    if (value != null) {
        return this.and(Op.build { op() })
    }
    return this
}

/**
 *
 * Ref:
 *
 * - [How to add multiple or filter conditions based on incoming parameters using exposed?]
 *   (https://stackoverflow.com/questions/54361503/how-to-add-multiple-or-filter-conditions-based-on-incoming-parameters-using-expo)
 */
//fun Query.andWhere(
//    andPart: SqlExpressionBuilder.() -> Op<Boolean>
//) = adjustWhere {
//    val expr = Op.build { andPart() }
//    if (this == null) {
//        expr
//    } else {
//        this or expr
//    }
//}
//
//fun <ID: Comparable<ID>, T: EntityClass<ID, Entity<ID>>, V> T.tryFind(
//    value: V?,
//    andPart: SqlExpressionBuilder.(T)->Op<Boolean>
//): T {
//    if (value != null) {
//        this.find { andPart(this@tryFind) }
//    }
//    return this
//}