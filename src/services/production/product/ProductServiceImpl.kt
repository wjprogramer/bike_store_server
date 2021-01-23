package com.giant_giraffe.services.production.product

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.production.brand.BrandTable
import com.giant_giraffe.data.production.category.CategoryTable
import com.giant_giraffe.data.production.product.Product
import com.giant_giraffe.data.production.product.ProductEntity
import com.giant_giraffe.data.production.product.ProductTable
import com.giant_giraffe.data.production.product.ProductView
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

class ProductServiceImpl: ProductService {

    override fun create(product: Product): ProductView {
        if (
            product.name == null ||
            product.modelYear == null ||
            product.listPrice == null
        ) {
            throw Exception("")
        }

        return transaction {
            ProductEntity.new {
                name = product.name!!
                modelYear = product.modelYear!!
                listPrice = product.listPrice!!
                brandId = EntityID(product.brandId, BrandTable)
                categoryId = EntityID(product.categoryId, CategoryTable)
            }
        }.toView()
    }

    override fun getById(productId: Int): ProductView? {
        return transaction {
            ProductEntity
                .find { ProductTable.id eq productId }
                .firstOrNull()
        }?.toView()
    }

    override fun getList(page: Int, size: Int): PageableData<ProductView> {
        return transaction {
            val staffs = ProductEntity.all()
                .limit(size, offset = page * size)
                .map { it.toView() }

            val pageInfo = EntityUtility
                .getPageInfo(ProductEntity, page, size, staffs.size)

            PageableData(
                data = staffs,
                pageInfo = pageInfo
            )
        }
    }

    override fun update(product: Product): Int {
        return transaction {
            ProductEntity
                .find { ProductTable.id eq product.id }
                .firstOrNull() ?: throw Exception()

            ProductTable.update({ ProductTable.id eq product.id }) {
                product.name?.let { e -> it[name] = e }
                product.modelYear?.let { e -> it[modelYear] = e }
                product.listPrice?.let { e -> it[listPrice] = e }
                product.brandId?.let { e -> it[brandId] = EntityID(e, BrandTable) }
                product.categoryId?.let { e -> it[categoryId] = EntityID(e, CategoryTable) }
            }
        }
    }

    override fun delete(productId: Int): Boolean {
        var result = true

        transaction {
            val number = ProductTable.deleteWhere { ProductTable.id eq productId }
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }

}