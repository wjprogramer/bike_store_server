package com.giant_giraffe.services.production.product

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.production.product.Product

interface ProductService {

    fun create(product: Product): Product

    fun getById(productId: Int): Product?

    fun getList(
        page: Int, size: Int,
        keyword: String? = null,
        modelYear: Int? = null,
        brandId: Int? = null,
        categoryId: Int? = null,
        minListPrice: Int? = null,
        maxListPrice: Int? = null,
    ): PagedData<Product>

    fun update(product: Product): Int

    fun delete(productId: Int): Boolean

}