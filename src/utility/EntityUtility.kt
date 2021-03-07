package com.giant_giraffe.utility

import com.giant_giraffe.core.PageInfo
import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.BaseEntity
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction

object EntityUtility {

    /**
     * 之後改用 [getPageableData]
     */
    @Deprecated("")
    fun <ID: Comparable<ID>> getPageInfo(
        entity: EntityClass<ID, Entity<ID>>,
        page: Int,
        pageSize: Int,
        elementCount: Int,
        predicates: Op<Boolean>? = null,
    ): PageInfo {
        return transaction {
            val totalElements = entity.count(predicates)
            var totalPages = totalElements / pageSize
            if (totalElements % pageSize != 0) {
                totalPages++
            }

            PageInfo(
                size = pageSize,
                page = page,
                dataCount = elementCount,
                totalPages = totalPages,
                totalDataCount = totalElements
            )
        }
    }

    /**
     * FIXME: 需改成回傳 [M]
     */
    @Deprecated("先暫時不要用")
    fun <E, M, V> getPageableData(
        entity: IntEntityClass<E>, page: Int, pageSize: Int
    ): PagedData<V> where E: IntEntity, E: BaseEntity<M, V> {
        return transaction {
            val data = entity.all()
                .limit(pageSize, offset = page * pageSize)
                .map { it.toView() }

            val totalElements = entity.count()
            var totalPages = totalElements / pageSize
            if (totalElements % pageSize != 0) {
                totalPages++
            }

            val pageInfo = PageInfo(
                size = pageSize,
                page = page,
                dataCount = data.size,
                totalPages = totalPages,
                totalDataCount = totalElements
            )

            PagedData(
                data = data,
                pageInfo = pageInfo
            )
        }
    }

    fun getPageInfo(
        dataCount: Int,
        totalDataCount: Int,
        page: Int,
        size: Int
    ): PageInfo {
        var totalPages = totalDataCount / size
        if (totalDataCount % size != 0) {
            totalPages++
        }

        return PageInfo(
            size = size,
            page = page,
            dataCount = dataCount,
            totalPages = totalPages,
            totalDataCount = totalDataCount
        )
    }

}