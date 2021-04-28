package com.giant_giraffe.dao

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.BaseEntity
import com.giant_giraffe.data.BaseModel
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * @property ID ID type of Entity
 * @property E Entity
 * @property M Model
 */
open class BaseDao<ID: Comparable<ID>, E, M: BaseModel<*>>
    where E: Entity<ID>, E: BaseEntity<M, *>
{

    protected fun EntityClass<ID, E>.findAndGetPagedData(
        page: Int,
        size: Int,
        predicates: Op<Boolean>? = null,
        order: Array<Pair<Expression<*>, SortOrder>> = arrayOf(),
        load: ((E) -> M)? = null,
    ): PagedData<M> {
        var totalDataSize = 0

        val data = transaction {
            val allOrFilteredData = if (predicates != null) {
                find(predicates)
            } else {
                all()
            }
            totalDataSize = allOrFilteredData.count()

            allOrFilteredData
                .limit(size, offset = page * size)
                .orderBy(*order)
                .map { entity ->
                    if (load != null) {
                        load(entity)
                    } else {
                        entity.toModel()
                    }
                }
        }

        val pageInfo = EntityUtility.getPageInfo(
            size = size,
            page = page,
            dataCount = data.size,
            totalDataCount = totalDataSize,
        )

        return PagedData(
            data = data,
            pageInfo = pageInfo
        )
    }

}