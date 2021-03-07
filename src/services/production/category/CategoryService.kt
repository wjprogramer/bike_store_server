package com.giant_giraffe.services.production.category

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.production.category.Category

interface CategoryService {

    fun create(category: Category): Category

    fun getById(categoryId: Int): Category?

    fun getList(page: Int, size: Int): PagedData<Category>

    fun update(category: Category): Int

    fun delete(categoryId: Int): Boolean

    fun findAll(): List<Category>

}