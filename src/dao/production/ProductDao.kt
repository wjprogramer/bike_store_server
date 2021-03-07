package com.giant_giraffe.dao.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.production.brand.BrandTable
import com.giant_giraffe.data.production.category.CategoryTable
import com.giant_giraffe.data.production.product.Product
import com.giant_giraffe.data.production.product.ProductEntity
import com.giant_giraffe.data.production.product.ProductTable
import com.giant_giraffe.utility.EntityUtility
import com.giant_giraffe.utils.tryAnd
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

object ProductDao {

    fun create(product: Product): Product {
        return transaction {
            ProductEntity.new {
                name = product.name!!
                modelYear = product.modelYear!!
                listPrice = product.listPrice!!
                brandId = EntityID(product.brandId, BrandTable)
                categoryId = EntityID(product.categoryId, CategoryTable)
            }
        }.toModel()
    }

    fun getById(productId: Int): Product? {
        return transaction {
            ProductEntity
                .find { ProductTable.id eq productId }
                .firstOrNull()
        }?.toModel()
    }

    fun find(
        page: Int,
        size: Int,
        keyword: String? = null,
        modelYear: Int? = null,
        brandId: Int? = null,
        categoryId: Int? = null,
        minListPrice: Int? = null,
        maxListPrice: Int? = null,
    ): PagedData<Product> {
        return transaction {

            var predicates: Op<Boolean> = Op.build { Op.TRUE }

            // FIXME: keyword
            predicates = predicates.tryAnd(keyword) { ProductTable.name match keyword!! }
            predicates = predicates.tryAnd(modelYear) { ProductTable.modelYear eq modelYear!! }
            predicates = predicates.tryAnd(brandId) { ProductTable.brandId eq brandId!! }
            predicates = predicates.tryAnd(categoryId) { ProductTable.categoryId eq categoryId!! }
            predicates = predicates.tryAnd(minListPrice) { ProductTable.listPrice greaterEq minListPrice!! }
            predicates = predicates.tryAnd(maxListPrice) { ProductTable.listPrice lessEq maxListPrice!! }

            val products = ProductEntity
                .find(predicates)

            val pagedProducts = products
                .limit(size, offset = page * size)
                .map { it.toModel() }

            PagedData(
                data = pagedProducts,
                pageInfo = EntityUtility.getPageInfo(
                    size = size,
                    page = page,
                    dataCount = pagedProducts.size,
                    totalDataCount = products.count(),
                )
            )
        }
    }

    fun update(product: Product): Int {
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

    fun delete(productId: Int): Int {
        return transaction {
            ProductTable.deleteWhere { ProductTable.id eq productId }
        }
    }

}