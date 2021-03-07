package com.giant_giraffe.dao.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.production.category.Category
import com.giant_giraffe.data.production.category.CategoryEntity
import com.giant_giraffe.data.production.category.CategoryTable
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

object CategoryDao {

    fun create(category: Category): Category {
        return transaction {
            CategoryEntity.new {
                name = category.name!!
            }
        }.toModel()
    }

    fun getById(categoryId: Int): Category? {
        return transaction {
            CategoryEntity
                .find { CategoryTable.id eq categoryId }
                .firstOrNull()
        }?.toModel()
    }

    fun find(page: Int, size: Int): PagedData<Category> {
        var totalDataSize = 0

        val categories = transaction {
            val allData = CategoryEntity.all()
            totalDataSize = allData.count()

            allData
                .limit(size, offset = page * size)
                .map { it.toModel() }
        }

        val pageInfo = EntityUtility
            .getPageInfo(
                dataCount = categories.size,
                totalDataCount = totalDataSize,
                page = page,
                size = size,
            )

        return PagedData(
            data = categories,
            pageInfo = pageInfo
        )
    }

    fun findAll(): List<Category> {
        return transaction {
            CategoryEntity.all()
                .map { it.toModel() }
        }
    }

    fun update(category: Category): Int {
        return transaction {
            CategoryEntity
                .find { CategoryTable.id eq category.id }
                .firstOrNull() ?: throw Exception()

            CategoryTable.update({ CategoryTable.id eq category.id }) {
                category.name?.let { e -> it[name] = e }
            }
        }
    }

    fun delete(categoryId: Int): Int {
        return transaction {
            CategoryTable.deleteWhere { CategoryTable.id eq categoryId }
        }
    }

}