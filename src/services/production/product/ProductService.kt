package com.giant_giraffe.services.production.product

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.production.product.Product
import com.giant_giraffe.data.production.product.ProductView
import com.giant_giraffe.data.sales.customer.CustomerView

interface ProductService {

    fun create(product: Product): Product

    fun getById(productId: Int): Product?

    fun getList(page: Int, size: Int): PageableData<Product>

    fun update(product: Product): Int

    fun delete(productId: Int): Boolean

}