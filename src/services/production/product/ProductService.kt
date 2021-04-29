package com.giant_giraffe.services.production.product

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.production.product.Product
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.SortOrder

interface ProductService {

    fun create(product: Product): Product

    fun getById(productId: Int): Product?

    fun find(
        page: Int,
        size: Int,
        keyword: String? = null,
        modelYear: Int? = null,
        brandId: Int? = null,
        categoryId: Int? = null,
        minListPrice: Int? = null,
        maxListPrice: Int? = null,
        sort: Collection<Pair<Expression<*>, SortOrder>>? = null,
    ): PagedData<Product>

    fun update(product: Product): Int

    fun delete(productId: Int): Boolean

}