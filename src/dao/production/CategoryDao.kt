package com.giant_giraffe.dao.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.BaseDao
import com.giant_giraffe.data.production.category.Category
import com.giant_giraffe.data.production.category.CategoryEntity
import com.giant_giraffe.data.production.category.CategoryTable
import com.giant_giraffe.exceptions.NotFoundException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

object CategoryDao:
    BaseDao<Int, CategoryEntity, Category>()
{

    fun create(category: Category): Category {
        return transaction {
            CategoryEntity.new {
                name = category.name!!
                category.imageUrl?.let { e -> imageUrl = e }
            }.toModel()
        }
    }

    fun getById(categoryId: Int): Category? {
        return transaction {
            CategoryEntity
                .find { CategoryTable.id eq categoryId and
                        (CategoryTable.isDeleted eq false)
                }
                .firstOrNull()
                ?.toModel()
        }
    }

    fun find(page: Int, size: Int): PagedData<Category> {
        return CategoryEntity.findAndGetPagedData(
            page = page,
            size = size,
            predicates = Op.build { CategoryTable.isDeleted eq false },
            order = arrayOf(CategoryTable.id to SortOrder.ASC)
        )
    }

    fun findAll(): List<Category> {
        return transaction {
            CategoryEntity
                .find { CategoryTable.isDeleted eq false }
                .orderBy(CategoryTable.id to SortOrder.ASC)
                .map { it.toModel() }
        }
    }

    fun update(category: Category): Int {
        return transaction {
            CategoryEntity
                .find { CategoryTable.id eq category.id and
                        (CategoryTable.isDeleted eq false)
                }
                .firstOrNull() ?: throw NotFoundException()

            CategoryTable.update({ CategoryTable.id eq category.id }) {
                category.name?.let { e -> it[name] = e }
            }
        }
    }

    fun softDelete(categoryId: Int): Int {
        return transaction {
            CategoryEntity
                .find { CategoryTable.id eq categoryId and
                        (CategoryTable.isDeleted eq false)
                }
                .firstOrNull() ?: throw NotFoundException()

            CategoryTable.update({ CategoryTable.id eq categoryId }) {
                it[isDeleted] = true
            }
        }
    }

}