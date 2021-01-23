package com.giant_giraffe.services.production.category

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.production.category.Category
import com.giant_giraffe.data.production.category.CategoryEntity
import com.giant_giraffe.data.production.category.CategoryTable
import com.giant_giraffe.data.production.category.CategoryView
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

class CategoryServiceImpl: CategoryService {

    override fun create(category: Category): CategoryView {
        if (
            category.name == null
        ) {
            throw Exception("")
        }

        return transaction {
            CategoryEntity.new {
                name = category.name!!
            }
        }.toView()
    }

    override fun getById(categoryId: Int): CategoryView? {
        return transaction {
            CategoryEntity
                .find { CategoryTable.id eq categoryId }
                .firstOrNull()
        }?.toView()
    }

    override fun getList(page: Int, size: Int): PageableData<CategoryView> {
        return transaction {
            val categories = CategoryEntity.all()
                .limit(size, offset = page * size)
                .map { it.toView() }

            val pageInfo = EntityUtility
                .getPageInfo(CategoryEntity, page, size, categories.size)

            PageableData(
                data = categories,
                pageInfo = pageInfo
            )
        }
    }

    override fun update(category: Category): Int {
        return transaction {
            CategoryEntity
                .find { CategoryTable.id eq category.id }
                .firstOrNull() ?: throw Exception()

            CategoryTable.update({ CategoryTable.id eq category.id }) {
                category.name?.let { e -> it[name] = e }
            }
        }
    }

    override fun delete(categoryId: Int): Boolean {
        var result = true

        transaction {
            val number = CategoryTable.deleteWhere { CategoryTable.id eq categoryId }
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }
    
}