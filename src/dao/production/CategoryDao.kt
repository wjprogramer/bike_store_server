package com.giant_giraffe.dao.production

import com.giant_giraffe.core.PageableData
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

    fun getList(page: Int, size: Int): PageableData<Category> {
        return transaction {
            val categories = CategoryEntity.all()
                .limit(size, offset = page * size)
                .map { it.toModel() }

            val pageInfo = EntityUtility
                .getPageInfo(CategoryEntity, page, size, categories.size)

            PageableData(
                data = categories,
                pageInfo = pageInfo
            )
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