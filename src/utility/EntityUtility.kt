package com.giant_giraffe.utility

import com.giant_giraffe.core.PageInfo
import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.BaseEntity
import com.giant_giraffe.data.sales.order_item.OrderItemEntity
import com.giant_giraffe.data.sales.store.StoreEntity
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

object EntityUtility {

    /**
     * 之後改用 [getPageableData]
     */
    fun <ID: Comparable<ID>> getPageInfo(entity: EntityClass<ID, Entity<ID>>, page: Int, pageSize: Int, elementCount: Int): PageInfo {
        return transaction {
            val totalElements = entity.count()
            var totalPages = totalElements / pageSize
            if (totalElements % pageSize != 0) {
                totalPages++
            }

            PageInfo(
                size = pageSize,
                page = page,
                elements = elementCount,
                totalPages = totalPages,
                totalElements = totalElements
            )
        }
    }

    /**
     * FIXME: 需改成回傳 [M]
     */
    @Deprecated("先暫時不要用")
    fun <E, M, V> getPageableData(
        entity: IntEntityClass<E>, page: Int, pageSize: Int
    ): PageableData<V> where E: IntEntity, E: BaseEntity<M, V> {
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
                elements = data.size,
                totalPages = totalPages,
                totalElements = totalElements
            )

            PageableData(
                data = data,
                pageInfo = pageInfo
            )
        }
    }

}