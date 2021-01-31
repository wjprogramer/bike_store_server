package com.giant_giraffe.services.production.category

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.dao.production.CategoryDao
import com.giant_giraffe.data.production.category.Category
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class CategoryServiceImpl: CategoryService {

    private val dao = CategoryDao

    override fun create(category: Category): Category {
        if (
            category.name == null
        ) {
            throw Exception("")
        }

        return dao.create(category)
    }

    override fun getById(categoryId: Int): Category? {
        return dao.getById(categoryId)
    }

    override fun getList(page: Int, size: Int): PageableData<Category> {
        return dao.getList(page, size)
    }

    override fun update(category: Category): Int {
        return dao.update(category)
    }

    override fun delete(categoryId: Int): Boolean {
        var result = true

        transaction {
            val number = dao.delete(categoryId)
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }
    
}