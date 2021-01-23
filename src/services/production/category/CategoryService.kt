package com.giant_giraffe.services.production.category

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.production.category.Category
import com.giant_giraffe.data.production.category.CategoryView
import com.giant_giraffe.data.sales.customer.CustomerView

interface CategoryService {

    fun create(category: Category): CategoryView

    fun getById(categoryId: Int): CategoryView?

    fun getList(page: Int, size: Int): PageableData<CategoryView>

    fun update(category: Category): Int

    fun delete(categoryId: Int): Boolean

}