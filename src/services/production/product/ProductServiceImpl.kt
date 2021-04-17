package com.giant_giraffe.services.production.product

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.production.ProductDao
import com.giant_giraffe.data.production.product.Product
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class ProductServiceImpl: ProductService {

    private val dao = ProductDao

    override fun create(product: Product): Product {
        if (
            product.name == null ||
            product.modelYear == null ||
            product.listPrice == null
        ) {
            throw Exception("")
        }

        return dao.create(product)
    }

    override fun getById(productId: Int): Product? {
        return dao.getById(productId)
    }

    override fun find(page: Int,
                      size: Int,
                      keyword: String?,
                      modelYear: Int?,
                      brandId: Int?,
                      categoryId: Int?,
                      minListPrice: Int?,
                      maxListPrice: Int?,
    ): PagedData<Product> {
        return dao.find(
            page,
            size,
            keyword = keyword,
            modelYear = modelYear,
            brandId = brandId,
            categoryId = categoryId,
            minListPrice = minListPrice,
            maxListPrice = maxListPrice,
        )
    }

    override fun update(product: Product): Int {
        return dao.update(product)
    }

    override fun delete(productId: Int): Boolean {
        var result = true

        transaction {
            val number = dao.softDelete(productId)
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }

}