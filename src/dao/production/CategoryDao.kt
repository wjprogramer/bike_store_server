package com.giant_giraffe.dao.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.BaseDao
import com.giant_giraffe.data.production.category.Category
import com.giant_giraffe.data.production.category.CategoryEntity
import com.giant_giraffe.data.production.category.CategoryTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

object CategoryDao:
    BaseDao<Int, CategoryEntity, Category>()
{

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
        return CategoryEntity.findAndGetPagedData(
            page = page,
            size = size,
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